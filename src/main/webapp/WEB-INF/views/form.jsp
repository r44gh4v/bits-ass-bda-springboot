<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <div>
                <c:choose>
                    <c:when test="${book.id != null}">
                        <h1>Edit Book</h1>
                        <p class="subtitle">Update the book details</p>
                    </c:when>
                    <c:otherwise>
                        <h1>Add Book</h1>
                        <p class="subtitle">Create a new catalog entry</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <a class="button button-secondary" href="${pageContext.request.contextPath}/books">Back to list</a>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert">${errorMessage}</div>
        </c:if>

        <form class="form" method="post"
              action="${pageContext.request.contextPath}<c:choose><c:when test='${book.id != null}'>/books/update/${book.id}</c:when><c:otherwise>/books</c:otherwise></c:choose>">
            <input type="hidden" name="id" value="${book.id}">

            <div class="form-row">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${book.title}" required>
            </div>

            <div class="form-row">
                <label for="genre">Genre</label>
                <input type="text" id="genre" name="genre" value="${book.genre}" required>
            </div>

            <div class="form-row">
                <label for="authorId">Author</label>
                <select id="authorId" name="authorId" required>
                    <option value="" disabled <c:if test="${book.author == null}">selected</c:if>>Select an author</option>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}" <c:if test="${book.author != null && author.id == book.author.id}">selected</c:if>>
                            <c:out value="${author.name}" /> (<c:out value="${author.nationality}" />)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="actions">
                <button class="button" type="submit">Save</button>
                <a class="button button-secondary" href="${pageContext.request.contextPath}/books">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
