package com.aki.goosinsa.domain.entity.user;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name="ACCOUNT_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="ACCOUNT_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    private Long id;
    private String email;

}
