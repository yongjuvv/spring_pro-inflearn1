package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//스프링 컨테이너와 함께 테스트를 실행한다. 실제 스프링을 이용해서 테스트를 할 수 있음.
@Transactional
//테스트에 @Transactional 어노테이션을 추가하면 DB에 insert와 같은 트랜잭션을 실행하고 테스트가 끝나면 테스트 이전 상태로 Db를 롤백(Rollback)하게 된다.

//통합테스트 구현 : 통합테스트는 보통 spring, DB를 함께 사용하여 테스트하는 것을 말함.
    //단위테스트는 단순 자바를 이용해서 각 기능별로 테스트하는 것이며, 일반적으로 단위테스트가 더 좋은 테스트임.
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/


        //then
    }



    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}