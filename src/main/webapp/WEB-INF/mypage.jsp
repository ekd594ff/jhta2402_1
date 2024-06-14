
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <h2>회원정보</h2>
    <tr>
        <th>USERNAME</th>
        <td>${infoMemberDTO.username}</td>
    </tr>
    <tr>
        <th>NICKNAME</th>
        <td>${infoMemberDTO.nickname}</td>
    </tr>
    <tr>
        <th>EMAIL</th>
        <td>${infoMemberDTO.email}</td>
    </tr>
    <tr>
        <th>IMAGEURL</th>
        <td>${infoMemberDTO.imageUrl}</td>
    </tr>
    <tr>
        <th>INTRODUCTION</th>
        <td>${infoMemberDTO.imageUrl}</td>
    </tr>
    <tr>
        <th>CREATEDAT</th>
        <td>${infoMemberDTO.createdAt}</td>
    </tr>
    <tr>
        <th>UPDATEDAT</th>
        <td>${infoMemberDTO.updatedAt}</td>
    </tr>
</div>

