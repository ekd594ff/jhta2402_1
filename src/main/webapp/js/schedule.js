const modalTitle = $('#modal-title');
const modalEventAdd = $('#modal-add-event');
const inputEventId = $('#input-event-id');
const inputStartDate = $('#input-start-date');
const inputEndDate = $('#input-end-date');
const inputTitle = $('#input-title');
const inputContent = $('#input-content');
const inputGroup = $('#input-group');

const buttonEventAdd = $('#button-event-add');
const buttonEventDelete = $('#button-event-delete');
const divGroup = $('#div-group');
const divInputGroup = $('#div-input-group');

let username;

const colorArray = ['#1a8fe3', '#dc0073', '#8f00ff', '#ff600a', '#04e762', '#f00c18', '#00d37b', '#1fd224'];
const textColorArray = ['#ffffff', 'white', 'white', 'white', 'black', 'white', 'black', 'black'];

let followGroupInfo = [];
let followGroupIdArray = [];

let inputGroupIdArray = [];
let hiddenGroupIdArray = [];

let activePopoverEvent = null;

let calendarInstance = null;

let groupCount = 0;

function onClickPopover(event) {
    event.stopPropagation();
}

document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        themeSystem: 'bootstrap5',
        locale: 'en',
        headerToolbar: {
            left: 'title,prev,next',
            center: '',
            right: '',
        },
        dayMaxEvents: false,
        editable: false,
        selectable: true,
        events: {
            url: '/schedule/list',
            method: 'POST',
            failure: function () {
                alert('문제가 발생했습니다. 다시 시도해 주세요.');
            },
            success: function (data) {
                const groupIdSet = new Set();
                groupCount = data.reduce((acc,cur) => {
                    const {groupID} = cur;
                    if(groupID !== undefined) {
                        if(groupIdSet.has(groupID)) {
                            return acc;
                        } else {
                            groupIdSet.add(groupID);
                            return acc + 1;
                        }
                    }
                }, -1);
            }
        },
        eventSourceSuccess: function (content, response) {
            username = content.username;
            followGroupInfo = content.groups;
            return content.events;
        },
        eventDataTransform: function (eventData) {
            let id = eventData.groupID;
            id = (!id) ? 0 : id;
            let colorIndex;
            let editable = eventData.editor === username;

            let groupname;

            if (id === 0) {
                groupname = '개인 일정';
            } else {
                let followIndex = followGroupInfo.findIndex(function (group) {
                    return group.id === id;
                });
                groupname = followGroupInfo[followIndex].groupname;
            }

            if (editable && !inputGroupIdArray.includes(id)) {
                inputGroupIdArray.push(id);
                inputGroup.append("<option value=\"" + id + "\">" + groupname + "</option>");
            }

            if (!followGroupIdArray.includes(id)) {

                console.log(groupCount);

                let colorIndex = (groupCount - followGroupIdArray.length) % colorArray.length;

                followGroupIdArray.push(id);

                const item = groupToggleItem({id, bgColor : colorArray[colorIndex], name: groupname});

                divGroup.append(item);

                $('#span-group-' + id).on('click', function () {
                    if ($(this).hasClass('hidden')) {
                        $(this).removeClass('hidden');
                        $(this).css({
                            'background-color': colorArray[colorIndex]
                        });
                        hiddenGroupIdArray.splice(hiddenGroupIdArray.indexOf(id), 1);
                    } else {
                        $(this).addClass('hidden');
                        $(this).css({
                            'background-color': '#D3D3D3'
                        });

                        hiddenGroupIdArray.push(id);
                    }

                    calendar.removeAllEvents();
                    calendar.refetchEvents();
                });
            }

            colorIndex = (groupCount - followGroupIdArray.indexOf(id)) % colorArray.length;

            return {
                id: eventData.id,
                groupId: eventData.id,
                title: eventData.title,
                start: stringToDate(eventData.startDate),
                end: stringToDate(eventData.endDate),
                editable: editable,
                extendedProps: {
                    groupId: id,
                    content: eventData.content,
                    editor: eventData.editor
                },
                backgroundColor: colorArray[colorIndex],
                textColor: textColorArray[colorIndex],
            };
        },
        // trigger when group toggle
        eventDidMount: function (info) {
            const {el} = info;

            if (hiddenGroupIdArray.includes(info.event.extendedProps.groupId)) {
                $(el).hide();
            } else {
                $(el).show();
            }
        },
        select: function (selectionInfo) {
            //when select multiple cells
            const { startStr ,endStr , jsEvent } = selectionInfo;
            jsEvent.stopImmediatePropagation();
            const popover = document.querySelector("#event-popover");

            setAddEventPopover({startDate : startStr, endDate : endStr});

            popover.querySelector(".popover-list").style.display = "none";
            popover.querySelector(".popover-list.add").style.display = "flex";

            setTimeout(() => {
                popover.classList.add("show");
                popover.classList.add("tail-center");
            }, 10);

            const scroll = document.body.scrollTop;

            const startPoint = document.querySelector(`td.fc-day[data-date="${startStr}"]`);
            const {top, left, width, height} = startPoint.getBoundingClientRect();

            popover.style.top = scroll + top + height/2 + "px";
            popover.style.left
                = (left + width/2 > window.innerWidth ? window.innerWidth - width/2 - 40 : left - width/2) + "px";

        },
        eventClick: function (info) {
            if (username !== info.event.extendedProps.editor) return;

            info.jsEvent.stopPropagation();

            const {el} = info;
            const popover = document.querySelector("#event-popover");
            const rect = el.getBoundingClientRect();

            popover.querySelector(".popover-list").style.display = "flex";
            popover.querySelector(".popover-list.add").style.display = "none";

            if(el === activePopoverEvent) {
                popover.classList.toggle("show");
                setEventPopoverPosition(popover, rect);
                return;
            }

            popover.classList.add("show");
            activePopoverEvent = el;
            setEventPopoverPosition(popover, rect);

            const data = {
                title : info.event.title,
                content : info.event.extendedProps.content,
                startDate: stringToDate(info.event.start),
                endDate : stringToDate(info.event.end),
                id: info.event.id
            };

            setPopOver(data);
        },

        eventChange: function (info) {
            info.event.extendedProps.groupId = info.oldEvent.groupId;
            info.event.groupId = info.event.id;

            const event = {
                id: info.event.id,
                groupID: info.event.extendedProps.groupId,
                title: info.event.title,
                content: info.event.extendedProps.content,
                startDate: stringToDate(info.event.start),
                endDate: stringToDate(info.event.end)
            };

            fetch('/schedule/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(event)
            })
                .then(response => {
                    if (!response.ok) {
                        throw response;
                    }
                    return response.json();
                })
                .then(res => {
                })
                .catch(error => {
                    error.json().then(err => {
                        info.revert();
                        alert('문제가 발생했습니다. 다시 시도해 주세요.');
                    });
                });
        }
    });

    calendar.render();

    calendarInstance = calendar;

    //
    buttonEventAdd.on('click', function () {
        if (checkIsEmpty(inputTitle.val(), inputContent.val(), inputGroup.val(), inputStartDate.val())) {
            return;
        }

        if (modalTitle.text() === '일정 추가') {
            const event = {
                groupID: inputGroup.val(),
                title: inputTitle.val(),
                content: inputContent.val(),
                startDate: inputStartDate.val(),
                endDate: inputEndDate.val()
            };

            fetch('/schedule/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(event)
            })
                .then(response => {
                    if (!response.ok) {
                        throw response;
                    }
                    return response.json();
                })
                .then(res => {
                    calendar.removeAllEvents();
                    calendar.refetchEvents();
                    alert('생성되었습니다.');
                })
                .catch(error => {
                    error.json().then(err => {
                        event.revert();
                        alert('문제가 발생했습니다. 다시 시도해 주세요.');
                    });
                });
        } else if (modalTitle.text() === '일정 변경') {

            const event = {
                id: inputEventId.val(),
                groupID: inputGroup.val(),
                title: inputTitle.val(),
                content: inputContent.val(),
                startDate: inputStartDate.val(),
                endDate: inputEndDate.val()
            };

            fetch('/schedule/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(event)
            })
                .then(response => {
                    if (!response.ok) {
                        throw response;
                    }
                    return response.json();
                })
                .then(res => {
                    calendar.removeAllEvents();
                    calendar.refetchEvents();
                    alert('수정되었습니다.');
                })
                .catch(error => {
                    error.json().then(err => {
                        alert('문제가 발생했습니다. 다시 시도해 주세요.');
                    });
                });
        }
        modalEventAdd.modal('toggle');
    });

    buttonEventDelete.on('click', function() {
        let eventID = inputEventId.val();
        if (confirm("삭제하시겠습니까?")) {
            fetch('/schedule/delete?id=' + eventID, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
                .then(response => {
                    console.log(response);
                    if (!response.ok) {
                        throw response;
                    }
                    return response.json();
                })
                .then(res => {
                    calendar.getEventById(eventID).remove();
                })
                .catch(error => {
                    error.json().then(err => {
                        alert('문제가 발생했습니다. 다시 시도해 주세요.');
                    });
                });
        }
        modalEventAdd.modal('toggle');
    });
});

function stringToDate(dateStr) {
    let date = new Date(dateStr);
    let year = date.getFullYear();
    let month = ("0" + (date.getMonth() + 1)).slice(-2); // Months are zero based
    let day = ("0" + date.getDate()).slice(-2);

    return year + '-' + month + '-' + day;
}

function setModal(title, buttonText, eventId, groupId, start, end, eventTitle, eventContent) {
    modalTitle.text(title);
    buttonEventAdd.text(buttonText);
    inputEventId.val(eventId);
    inputGroup.val(groupId);
    inputStartDate.val(start);
    inputEndDate.val(end);
    inputTitle.val(eventTitle);
    inputContent.val(eventContent);
}

document.addEventListener("click", () => {
    const popoverEl =  document.querySelector("#event-popover");
    popoverEl.classList.remove("show");
    popoverEl.classList.remove("tail-center");
});


document.querySelector("#event-popover").addEventListener("click", onClickPopover);

function setPopOver(data) {
    const {title, content, startDate, endDate, id} = data;
    document.querySelector("#event-title").value = title;
    document.querySelector("#event-content").value = content;
    document.querySelector("#event-start-date").value = startDate;
    document.querySelector("#event-end-date").value = endDate;
    document.querySelector("#event-id").value = id;
}

function onClickEventEditBtn(event) {
    const choice = window.confirm("일정을 변경하시겠습니까?");
    if(choice) {
        const editedEvent = {
            title : document.querySelector("#event-title").value,
            content : document.querySelector("#event-content").value,
            startDate : document.querySelector("#event-start-date").value,
            endDate : document.querySelector("#event-end-date").value,
            id : document.querySelector("#event-id").value
        };
        fetch('/schedule/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(editedEvent)
        })
            .then(response => {
                if(response.ok) {
                    return response.json();
                }
                throw new Error("");
            })
            .then(data => {
                calendarInstance?.removeAllEvents();
                calendarInstance?.refetchEvents();
                alert('수정되었습니다');
                const popover = document.querySelector("#event-popover");
                popover.classList.toggle("show");
            })
            .catch(error => {
                alert('문제가 발생했습니다. 다시 시도해 주세요');
            });
    }
}

function onClickEventDeleteBtn(event) {
    const choice = window.confirm("일정을 삭제하시겠습니까?");
    if(choice) {
        const eventId = document.querySelector("#event-id").value;
        fetch(`/schedule/delete?id=${eventId}`, {
            method : "DELETE"
        }).then((result) => {
            if(!result.ok) {
                throw new Error("");
            }
            return result.json();
        }).then(data => {
            alert("삭제되었습니다");
            calendarInstance?.removeAllEvents();
            calendarInstance?.refetchEvents();
            const popover = document.querySelector("#event-popover");
            popover.classList.toggle("show");
        }).catch(err => {
            alert('문제가 발생했습니다. 다시 시도해 주세요.');
        });
    }
}

function checkIsEmpty(title, content, groupId, start) {
    if (!title) {
        alert('제목을 입력해주세요.');
    } else if (!content) {
        alert('내용을 입력해주세요.');
    } else if (!groupId) {
        alert('그룹을 선택해주세요.');
    } else if (!start) {
        alert('시작날짜를 입력해주세요.');
    } else {
        return false;
    }

    return true;
}

function setEventPopoverPosition(popover, rect) {
    const scroll = document.body.scrollTop;
    const popoverWidth = popover.getBoundingClientRect().width;
    popover.style.top = scroll + rect.top + 28 + "px";
    // when popover over window, optimize popover x position
    popover.style.left
        = (rect.left + popoverWidth > window.innerWidth ? window.innerWidth - popoverWidth - 40 : rect.left) + "px";
}

function setAddEventPopover(data) {
    const {startDate, endDate} = data;
    document.querySelector("#add-event-start-date").value = startDate;
    document.querySelector("#add-event-end-date").value = endDate;
}

function onClickCreateEvent(event) {
    const newEvent = {
        groupID: 0,
        title: document.querySelector("#add-event-title").value,
        content: document.querySelector("#add-event-content").value,
        startDate: document.querySelector("#add-event-start-date").value,
        endDate: document.querySelector("#add-event-end-date").value
    };

    fetch('/schedule/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newEvent)
    })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(res => {
            calendarInstance?.removeAllEvents();
            calendarInstance?.refetchEvents();
            alert('생성되었습니다');
            const popover = document.querySelector("#event-popover");
            popover.classList.toggle("show");
            emptyCreateEventForm();
        })
        .catch(error => {
            error.json().then(err => {
                event.revert();
                alert('문제가 발생했습니다 다시 시도해 주세요');
            });
        });
}

function emptyCreateEventForm() {
    document.querySelector("#add-event-title").value = "";
    document.querySelector("#add-event-content").value = "";
    document.querySelector("#add-event-start-date").value = "";
    document.querySelector("#add-event-end-date").value = "";
}