<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 결제 화면</title>
</head>
<body>
	<h2>카드 결제 화면</h2>
	<form action="buy_ticket_card">
		고객 아이디 : <input type="text" name="consumerid"><br><br>
		티켓 구매수 : <input type="text" name="amount"><br><br>
		<input type="submit" value="결제">	
	</form>
</body>
</html>