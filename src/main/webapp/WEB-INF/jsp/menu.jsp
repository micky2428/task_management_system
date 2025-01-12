<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>
                Coffee Shop Menu
            </title>
            <link rel="stylesheet" href="/css/styles.css">
        </head>
    <body>
        <h1>
            Welcome to the Coffee Shop!
        </h1>
        <h2>
            Our Menu
        </h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>$${product.price}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>

<!-- JSP 是一種動態網頁技術，它基於 Java。JSP 允許在 HTML 中嵌入 Java 程式碼，使得網頁可以根據用戶的請求動態生成內容 -->