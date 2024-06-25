function groupToggleItem(props) {
    const {id, bgColor, name} = props;

    const groupToggleItem = document.createElement("span");
    groupToggleItem.classList.add("group-toggle-item");
    groupToggleItem.id = `span-group-${id}`;
    groupToggleItem.style.border = `1px solid ${bgColor}`;

    const groupColorIcon = document.createElement("span");
    groupColorIcon.classList.add("group-color");
    groupColorIcon.style.backgroundColor = bgColor;

    const groupNameSpan = document.createElement("span");
    groupNameSpan.classList.add("group-name");
    groupNameSpan.textContent = name;

    groupToggleItem.append(groupColorIcon, groupNameSpan);

    return groupToggleItem;
}