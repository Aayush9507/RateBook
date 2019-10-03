
<%@ page import="RateITProd.RateItems" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rateItems.label', default: 'RateItems')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-rateItems" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-rateItems" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="itemDesc" title="${message(code: 'rateItems.itemDesc.label', default: 'Item Desc')}" />
					
						<g:sortableColumn property="itemFollowCount" title="${message(code: 'rateItems.itemFollowCount.label', default: 'Item Follow Count')}" />
					
						<g:sortableColumn property="itemLastUpdated" title="${message(code: 'rateItems.itemLastUpdated.label', default: 'Item Last Updated')}" />
					
						<g:sortableColumn property="itemName" title="${message(code: 'rateItems.itemName.label', default: 'Item Name')}" />
					
						<g:sortableColumn property="itemReviewCount" title="${message(code: 'rateItems.itemReviewCount.label', default: 'Item Review Count')}" />
					
						<g:sortableColumn property="itemType" title="${message(code: 'rateItems.itemType.label', default: 'Item Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${rateItemsInstanceList}" status="i" var="rateItemsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${rateItemsInstance.id}">${fieldValue(bean: rateItemsInstance, field: "itemDesc")}</g:link></td>
					
						<td>${fieldValue(bean: rateItemsInstance, field: "itemFollowCount")}</td>
					
						<td><g:formatDate date="${rateItemsInstance.itemLastUpdated}" /></td>
					
						<td>${fieldValue(bean: rateItemsInstance, field: "itemName")}</td>
					
						<td>${fieldValue(bean: rateItemsInstance, field: "itemReviewCount")}</td>
					
						<td>${fieldValue(bean: rateItemsInstance, field: "itemType")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${rateItemsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
