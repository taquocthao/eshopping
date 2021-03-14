<%--
  Created by IntelliJ IDEA.
  User: thao.ta-quoc
  Date: 3/11/2021
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<security:authorize access="hasAuthority('ADMIN')">
    <c:redirect url="/admin/home.html"/>
</security:authorize>
<c:redirect url="/home.html"/>
