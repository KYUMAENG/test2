package com.multi.campus;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.service.TransactionService;

@Controller
public class TransactionController {
	@Inject
	TransactionService service;
	
	//********************트랜잭션 객체 DI************
	
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@GetMapping("/transaction")
	public ModelAndView transactionTest(HttpSession session) {
		//** transaction처리를 위해 객체를 생성
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		
		try {
			BoardDTO dto1 = new BoardDTO();
			dto1.setSubject("트랜잭션 테스트 1");
			dto1.setContent("글내용1");
			dto1.setUserid((String)session.getAttribute("logId"));
			dto1.setIp("123.123.123.123");
			
			BoardDTO dto2 = new BoardDTO();
			dto2.setSubject("트랜잭션 테스트 2");
			dto2.setContent("글내용2");
			dto2.setUserid((String)session.getAttribute("logId"));
			dto2.setIp("123.123.123.123");
			
			service.tranBoardInsert(dto1);
			
			service.tranBoardInsert(dto2);
			
			//레코드 2개 등록이됨
			transactionManager.commit(status);
		}catch(Exception e) {
			e.printStackTrace();
			//2개의 레코드 중 1개 이상이 예외가 발생할때 insert된 레코드를 취소한다.
			transactionManager.rollback(status);
		}
		return mav;
	}
}
