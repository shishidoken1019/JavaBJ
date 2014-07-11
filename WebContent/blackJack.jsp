<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Srtuts2 BlackJack</title>
</head>
<body>
<h1><s:property value="game_name" /></h1>
<h2>親の手札</h2>
<ul>
<s:iterator value="parent_card" status="rowstatus">	

<s:if test="#rowstatus.index==0">
	<li><s:property value="parent_card.get(#rowstatus.index)" /></li>
</s:if>
<s:else>
	<s:if test="result.length() != 0">
		<li><s:property value="parent_card.get(#rowstatus.index)" /></li>
	</s:if>
	<s:else>
		<li>■</li>
	</s:else>
</s:else>	
</s:iterator>
</ul>

<h2>自分の手札</h2>
<ul>
<s:iterator value="player_card" status="rowstatus">	
	<li><s:property value="player_card.get(#rowstatus.index)" /></li>
</s:iterator>
</ul>
<s:form name="form1">
<s:if test="result.length() == 0">
		<p><s:submit method="hit" value="ヒット"/></p>
		<p><s:submit method="stand" value="スタンド"/></p>
</s:if>
</s:form>

<s:if test="result.length() != 0">
	<h4><font color="red"><s:property value="result" /></font></h4>
	<p>あなたのカードの合計 : <s:property value="player_sum_card" /></p>
	<p>親のカードの合計 : <s:property value="parent_sum_card" /></p>
	<p><a href=./blackJack.action>継続してブラックジャックを遊ぶ</a></p>
	<p><a href=./index.action>ゲームを変更する</a></p>
</s:if>
</body>
</html>