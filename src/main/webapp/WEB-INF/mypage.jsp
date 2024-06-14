
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <h1>회원정보</h1>
    <div class="username">
        <th>USERNAME</th><br>
        <td>${infoMemberDTO.username}</td>
    </div>
    <div class="nickname">
        <th>NICKNAME</th><br>
        <td>${infoMemberDTO.nickname}</td>
    </div>
    <div class="email">
        <th>EMAIL</th><br>
        <td>${infoMemberDTO.email}</td>
    </div>
    <div class="imageUrl">
        <th>IMAGEURL</th><br>
        <td>${infoMemberDTO.imageUrl}</td>
    </div>
    <div class="introduction">
        <th>INTRODUCTION</th><br>
        <td>${infoMemberDTO.imageUrl}</td>
    </div>
    <div class="createdAt">
        <th>CREATEDAT</th><br>
        <td>${infoMemberDTO.createdAt}</td>
    </div>
    <div class="updatedAt">
        <th>UPDATEDAT</th><br>
        <td>${infoMemberDTO.updatedAt}</td>
    </div>
</div>

