package com.gxh.shop.service.impl;


import com.gxh.shop.mapper.SMemberLevelMapper;
import com.gxh.shop.mapper.SMemberMapper;
import com.gxh.shop.model.SMember;
import com.gxh.shop.model.SMemberExample;
import com.gxh.shop.model.SMemberLevel;
import com.gxh.shop.model.SMemberLevelExample;
import com.gxh.shop.bo.MemberDetails;
import com.gxh.shop.service.MemberService;
import com.gxh.shop.security.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author gxh
 */
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SMemberMapper memberMapper;
    @Autowired
    private SMemberLevelMapper memberLevelMapper;
   

    @Override
    public SMember getByUsername(String username) {
        SMember member;
        SMemberExample example = new SMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SMember> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            member = memberList.get(0);
            return member;
        }
        return null;
    }

    @Override
    public SMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String username, String password, String telephone, String authCode) {
        //???????????????
        verifyAuthCode(authCode, telephone);// Asserts.fail("???????????????");
//???????????????????????????
        SMemberExample example = new SMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or(example.createCriteria().andPhoneEqualTo(telephone));
        List<SMember> umsMembers = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMembers)) {
           // Asserts.fail("?????????????????????");
        }
        //?????????????????????????????????
        SMember umsMember = new SMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //?????????????????????????????????
        SMemberLevelExample levelExample = new SMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<SMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(umsMember);
        umsMember.setPassword(null);
    }


    //?????????????????????????????????
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        //String realAuthCode = memberCacheService.getAuthCode(telephone);
        return "realAuthCode".equals(authCode);
    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        //memberCacheService.setAuthCode(telephone,sb.toString());
        return sb.toString();
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        SMemberExample example = new SMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<SMember> memberList = memberMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(memberList)){
            //Asserts.fail("??????????????????");
        }
        //???????????????
        if(!verifyAuthCode(authCode,telephone)){
           // Asserts.fail("???????????????");
        }
        SMember umsMember = memberList.get(0);
        umsMember.setPassword(passwordEncoder.encode(password));
        memberMapper.updateByPrimaryKeySelective(umsMember);
       // memberCacheService.delMember(umsMember.getId());
    }

    @Override
    public SMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getMember();
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        SMember record=new SMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SMember member = getByUsername(username);
        if(member!=null){
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("????????????????????????");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //????????????????????????????????????
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("???????????????");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("????????????:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }
}
