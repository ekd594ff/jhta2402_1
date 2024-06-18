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

let username;

const colorArray = ['yellow', 'blue', 'green', 'purple', 'orange', 'red', 'skyblue', 'gray'];
const textColorArray = ['black', 'white', 'white', 'white', 'black', 'white', 'black', 'black'];

let followGroupArray = [];
let followGroupIdArray = [];

let groupIdArray = [];
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
        editable: true,
        selectable: true,

        events: {
            url: '/schedule/list',
            method: 'POST',
            failure: function () {
                alert('there was an error while fetching events!');
            },
        },
        eventSourceSuccess: function (content, response) {
            username = content.username;
            followGroupArray = content.groups;

            return content.events;
        },
        eventDataTransform: function (eventData) {
            let id = eventData.groupID;
            id = (!id) ? 0 : id;
            let colorIndex;

            if (eventData.editor === username && !followGroupIdArray.includes(id)) {
                followGroupIdArray.push(id);
                inputGroup.append("<option value=\"" + id + "\">" + id + "</option>");
            }

            if (!groupIdArray.includes(id)) {
                groupIdArray.push(id);
                divGroup.append("<span id='span-group-" + id + "' class='badge d-flex align-items-center p-1 pe-2 text-primary-emphasis border border-secondary-subtle rounded-pill' style='font-size: 14px'>" +
                    +id + " sampleGroupName</button>");

                $('#span-group-' + id).on('click', function () {
                    if ($(this).hasClass('hidden')) {
                        $(this).removeClass('hidden');
                        $(this).css('background', 'none');

                        hiddenGroupIdArray.splice(hiddenGroupIdArray.indexOf(id), 1);
                        console.log(hiddenGroupIdArray);

                        calendar.removeAllEvents();
                        calendar.refetchEvents();
                    } else {
                        $(this).addClass('hidden');
                        $(this).css('background-color', 'gray');

                        hiddenGroupIdArray.push(id);
                        console.log(hiddenGroupIdArray);

                        calendar.removeAllEvents();
                        calendar.refetchEvents();
                    }
                });
            }

            colorIndex = groupIdArray.indexOf(id) % colorArray.length;

            return {
                id: eventData.id,
                groupId: eventData.id,
                title: eventData.title,
                start: eventData.startDate,
                end: eventData.endDate,
                extendedProps: {
                    groupId: id,
                    content: eventData.content
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
                    console.log(res);
                })
                .catch(error => {
                    console.log(error);
                    error.json().then(err => {
                        info.revert();
                        alert(err.status);
                    });
                });
        }
    });

    calendar.render();


    buttonEventAdd.on('click', function () {

        if (modalTitle.text() === '일정 추가') {
            tempId++;
            console.log(tempId);

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
                    alert(res.status);
                })
                .catch(error => {
                    error.json().then(err => {
                        event.revert();
                        alert(err.status);
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
                    alert(res.status);
                })
                .catch(error => {
                    error.json().then(err => {
                        alert(err.status);
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