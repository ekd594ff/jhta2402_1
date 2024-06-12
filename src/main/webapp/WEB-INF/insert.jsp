<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
    <div class="container">
        <h2 class="mt-5 mb-5">회원가입</h2>
        <form action="/insert-process" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="username" class="form-label">USER NAME</label>
                <input type="text" class="form-control" id="username" placeholder="user name" name="username">
                <button type="button" id="btn-duplicate" class="btn btn-dark mt-2">아이디 중복 체크</button>
                <%--<a href="" id="btn-duplicate02" class="btn btn-dark mt-2">아이디 중복 체크</a>--%>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">PASSWORD</label>
                <input type="password" class="form-control" id="password" placeholder="password" name="password">
                <div class="invalid-feedback"></div>
            </div>
            <div class="mb-3">
                <label for="password02" class="form-label">PASSWORD CONFIRM</label>
                <input type="password" class="form-control" id="password02" placeholder="password">
            </div>
            <div class="mb-3">
                <label for="nickname" class="form-label">NICKNAME</label>
                <input type="text" class="form-control" id="nickname" placeholder="nickname" name="nickname">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">EMAIL</label>
                <input type="email" class="form-control" id="email" placeholder="email" name="email">
            </div>
            <div class="mb-3">
                <label for="imageUrl" class="form-label">IMAGE URL</label>
                <input type="text" class="form-control" id="imageUrl" placeholder="image Url" name="imageUrl">
            </div>
            <div class="mb-3">
                <label for="introduction" class="form-label">INTRODUCTION</label>
                <input type="text" class="form-control" id="introduction" placeholder="introduction" name="introduction">
            </div>
        </form>
    </div>
<script>
    let isIdChecked=false;
    $("#btn-sign").on("click",function(){
        if($("#username").val().trim()===""){
            alert("username은 필수입력 사항입니다.");
            $("#username").focus();
            return false;
        }
        if($("#password").val().trim()===""){
            alert("password는 필수입력 사항입니다.");
            $("#password").focus();
            return false;
        }
        if($("#nickname").val().trim()===""){
            alert("nickname은 필수입력 사항입니다.");
            $("#nickname").focus();
            return false;
        }
        if($("#email").val().trim()===""){
            alert("email은 필수입력 사항입니다.");
            $("#email").focus();
            return false;
        }
        if($("#password").val()!==$("#password02").val()) {
            alert("패스워드가 맞지 않습니다.");
            $("#password02").focus();
            return false;
        }
        if(!isIdChecked){
            alert("아이디 중복체크해주세요.");
            $("#username").focus();
            return false;
        }
    });
    $("#password02").on("keyup",function(e) {
        //console.log("키를 눌렀다 뗐습니다.");
        if($("#password02").val()!==$("#password").val()) {
            $(".invalid-feedback").text("password가 맞질 않습니다.");
            $(".invalid-feedback").show();
        } else {
            $(".invalid-feedback").text("");
            $(".invalid-feedback").hide();
        }
    })
    $("#btn-duplicate").on("click",function(){
        $.ajax({
            url:"/member/id-check-process",
            data: {
                userID:$("#userID").val()
            },
            method:"post",
            success:function(data) {
                console.log(data);
                if(data.count>0) {
                    alert("쓸 수 없는 아이디입니다.");
                    $("#username").val("");
                    $("#username").focus();
                } else {
                    const used = confirm("쓸 수 있는 아이디입니다. 사용하시겠습니까?");
                    if(used) {
                        $("#username").attr("readonly",true);
                        isIdChecked=true;
                    } else {
                        $("#username").val("");
                        $("#username").focus();
                    }
                }
            },
            fail:function(error) {
                console.log(error);
            }
        });
    });
</script>