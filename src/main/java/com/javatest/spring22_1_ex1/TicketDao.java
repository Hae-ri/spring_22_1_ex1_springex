package com.javatest.spring22_1_ex1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TicketDao {

	JdbcTemplate template;
	TransactionTemplate transactionTemplate;
	
	PlatformTransactionManager transactionManager;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public TicketDao() {
		super();
	}
	
	public void buyTicket(final TicketDto dto) {
		
		// 카드 결제와 티켓 구매를 묶어주기
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// TODO Auto-generated method stub
				
				try {
				template.update(new PreparedStatementCreator() {
					
					// 카드 결제
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						// TODO Auto-generated method stub
						String query="insert into card (consumerid, amount) values (?,?)";
						
						PreparedStatement pstmt = con.prepareStatement(query);
						pstmt.setString(1,dto.getConsumerid());
						pstmt.setInt(2,dto.getAmount());
						
						return pstmt;
					}
				});
				
				// 티켓 구매(창구)
				template.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						String query="insert into ticket (consumerid, countNum) values (?,?)";
						
						PreparedStatement pstmt = con.prepareStatement(query);
						pstmt.setString(1,dto.getConsumerid());
						pstmt.setInt(2,dto.getAmount());
						
						return pstmt;
					}
				});
				
				transactionManager.commit(status);
				
				
			} catch(Exception e) {
				e.printStackTrace();
				
				HomeController cont = new HomeController();
				
				cont.countError();
				
				System.out.println("Rollback!");
			}
				
			}
		});
		
		
	}

	
}
