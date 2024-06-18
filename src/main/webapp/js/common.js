function updateHeaderProfileImage(data) {
    const { profileImgUrl } = data;
    if(profileImgUrl) {
        const avatarEl = document.querySelector(".header .avatar");
        avatarEl.classList.add("active");
        const avatarImageEl = avatarEl.querySelector(".avatar-img");
        avatarImageEl.setAttribute("src", profileImgUrl);
    }
}

function removeHeaderProfileImage() {
    const avatarEl = document.querySelector(".header .avatar");
    avatarEl.classList.remove("active");
    const avatarImageEl = avatarEl.querySelector(".avatar-img");
    avatarImageEl.setAttribute("src", "");
}

