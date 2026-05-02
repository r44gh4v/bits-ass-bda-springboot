<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Books</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <div>
                <h1>Library Catalog</h1>
                <p class="subtitle">Books and their authors</p>
            </div>
            <a class="button" href="${pageContext.request.contextPath}/books/new">Add Book</a>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Author</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td><c:out value="${book.title}" /></td>
                        <td><c:out value="${book.genre}" /></td>
                        <td><c:out value="${book.author.name}" /></td>
                        <td class="actions">
                            <a class="button button-secondary" href="${pageContext.request.contextPath}/books/edit/${book.id}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty books}">
                    <tr>
                        <td colspan="4" class="empty">No books found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
