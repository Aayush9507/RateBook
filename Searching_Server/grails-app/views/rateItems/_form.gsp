<%@ page import="RateITProd.RateItems" %>



<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemDesc', 'error')} ">
	<label for="itemDesc">
		<g:message code="rateItems.itemDesc.label" default="Item Desc" />
		
	</label>
	<g:textField name="itemDesc" value="${rateItemsInstance?.itemDesc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemFollowCount', 'error')} required">
	<label for="itemFollowCount">
		<g:message code="rateItems.itemFollowCount.label" default="Item Follow Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="itemFollowCount" type="number" value="${rateItemsInstance.itemFollowCount}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemLastUpdated', 'error')} required">
	<label for="itemLastUpdated">
		<g:message code="rateItems.itemLastUpdated.label" default="Item Last Updated" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="itemLastUpdated" precision="day"  value="${rateItemsInstance?.itemLastUpdated}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemName', 'error')} ">
	<label for="itemName">
		<g:message code="rateItems.itemName.label" default="Item Name" />
		
	</label>
	<g:textField name="itemName" value="${rateItemsInstance?.itemName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemReviewCount', 'error')} required">
	<label for="itemReviewCount">
		<g:message code="rateItems.itemReviewCount.label" default="Item Review Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="itemReviewCount" type="number" value="${rateItemsInstance.itemReviewCount}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'itemType', 'error')} ">
	<label for="itemType">
		<g:message code="rateItems.itemType.label" default="Item Type" />
		
	</label>
	<g:textField name="itemType" value="${rateItemsInstance?.itemType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rateItemsInstance, field: 'userEmail', 'error')} ">
	<label for="userEmail">
		<g:message code="rateItems.userEmail.label" default="User Email" />
		
	</label>
	<g:textField name="userEmail" value="${rateItemsInstance?.userEmail}"/>
</div>

