
<%@ page import="RateITProd.RateItems" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rateItems.label', default: 'RateItems')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-rateItems" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-rateItems" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list rateItems">
			
				<g:if test="${rateItemsInstance?.itemDesc}">
				<li class="fieldcontain">
					<span id="itemDesc-label" class="property-label"><g:message code="rateItems.itemDesc.label" default="Item Desc" /></span>
					
						<span class="property-value" aria-labelledby="itemDesc-label"><g:fieldValue bean="${rateItemsInstance}" field="itemDesc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.itemFollowCount}">
				<li class="fieldcontain">
					<span id="itemFollowCount-label" class="property-label"><g:message code="rateItems.itemFollowCount.label" default="Item Follow Count" /></span>
					
						<span class="property-value" aria-labelledby="itemFollowCount-label"><g:fieldValue bean="${rateItemsInstance}" field="itemFollowCount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.itemLastUpdated}">
				<li class="fieldcontain">
					<span id="itemLastUpdated-label" class="property-label"><g:message code="rateItems.itemLastUpdated.label" default="Item Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="itemLastUpdated-label"><g:formatDate date="${rateItemsInstance?.itemLastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.itemName}">
				<li class="fieldcontain">
					<span id="itemName-label" class="property-label"><g:message code="rateItems.itemName.label" default="Item Name" /></span>
					
						<span class="property-value" aria-labelledby="itemName-label"><g:fieldValue bean="${rateItemsInstance}" field="itemName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.itemReviewCount}">
				<li class="fieldcontain">
					<span id="itemReviewCount-label" class="property-label"><g:message code="rateItems.itemReviewCount.label" default="Item Review Count" /></span>
					
						<span class="property-value" aria-labelledby="itemReviewCount-label"><g:fieldValue bean="${rateItemsInstance}" field="itemReviewCount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.itemType}">
				<li class="fieldcontain">
					<span id="itemType-label" class="property-label"><g:message code="rateItems.itemType.label" default="Item Type" /></span>
					
						<span class="property-value" aria-labelledby="itemType-label"><g:fieldValue bean="${rateItemsInstance}" field="itemType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rateItemsInstance?.userEmail}">
				<li class="fieldcontain">
					<span id="userEmail-label" class="property-label"><g:message code="rateItems.userEmail.label" default="User Email" /></span>
					
						<span class="property-value" aria-labelledby="userEmail-label"><g:fieldValue bean="${rateItemsInstance}" field="userEmail"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${rateItemsInstance?.id}" />
					<g:link class="edit" action="edit" id="${rateItemsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
