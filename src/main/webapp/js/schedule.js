const modalTitle = $('#modal-title');
const modalEventAdd = $('#modal-add-event');
const inputEventId = $('#input-event-id');
const inputStartDate = $('#input-start-date');
const inputEndDate = $('#input-end-date');
const inputTitle = $('#input-title');
const inputContent = $('#input-content');
const inputGroup = $('#input-group');

const buttonEventAdd = $('#button-event-add');
const divGroup = $('#div-group');
const divInputGroup = $('#div-input-group');

let username;

const colorArray = ['yellow', 'blue', 'green', 'purple', 'orange', 'red', 'skyblue', 'gray'];
const textColorArray = ['black', 'white', 'white', 'white', 'black', 'white', 'black', 'black'];

let followGroupInfo = [];
let followGroupIdArray = [];

let inputGroupIdArray = [];
let hiddenGroupIdArray = [];


document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        themeSystem: 'bootstrap5',
        locale: 'ko',
        headerToolbar: {
            left: 'today',
            center: 'title',
            right: 'prev,next',
        },

        dayMaxEvents: true,
        editable: false,
        selectable: true,

        events: {
            url: '/schedule/list',
            method: 'POST',
            failure: function () {
                alert('문제가 발생했습니다. 다시 시도해 주세요.');
            },
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
                let colorIndex = (followGroupIdArray.length) % colorArray.length;
                followGroupIdArray.push(id);

                divGroup.append(
                    "<span id='span-group-" + id + "' " +
                    "class='badge d-flex align-items-center px-5 py-2 m-2 text-primary-emphasis" +
                    " border border-secondary-subtle rounded-pill' " +
                    "style='" +
                    "font-size: 14px; " +
                    "color: " + textColorArray[colorIndex] + " !important; " +
                    "background-color: " + colorArray[colorIndex] + ";" +
                    "'>" + groupname + "</span>");

                $('#span-group-' + id).on('click', function () {
                    if ($(this).hasClass('hidden')) {
                        $(this).removeClass('hidden');
                        $(this).css({
                            'background-color' : colorArray[colorIndex]
                        });

                        hiddenGroupIdArray.splice(hiddenGroupIdArray.indexOf(id), 1);
                    } else {
                        $(this).addClass('hidden');
                        $(this).css({
                            'background-color' : '#D3D3D3'
                        });

                        hiddenGroupIdArray.push(id);
                    }

                    calendar.removeAllEvents();
                    calendar.refetchEvents();
                });
            }

            colorIndex = followGroupIdArray.indexOf(id) % colorArray.length;

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
                textColor: textColorArray[colorIndex]
            };
        },
        // trigger when group toggle
        eventDidMount: function (info) {
            if (hiddenGroupIdArray.includes(info.event.extendedProps.groupId)) {
                $(info.el).hide();
            } else {
                $(info.el).show();
            }
        },
        select: function (selectionInfo) {
            divInputGroup.show();

            setModal(
                '일정 추가',
                '추가',
                0,
                null,
                stringToDate(selectionInfo.start),
                stringToDate(selectionInfo.end),
                '',
                ''
            );

            modalEventAdd.modal('toggle');
        },
        eventClick: function (info) {
            if (username !== info.event.extendedProps.editor) return;

            divInputGroup.hide();

            setModal(
                '일정 변경',
                '변경',
                info.event.id,
                info.event.extendedProps.groupId,
                stringToDate(info.event.start),
                stringToDate(info.event.end),
                info.event.title,
                info.event.extendedProps.content
            );

            modalEventAdd.modal('toggle');
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


    buttonEventAdd.on('click', function () {

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