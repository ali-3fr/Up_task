var msgList = [];

function run() {

    var appContainer = document.getElementById('chat-container');
    appContainer.addEventListener('click', delegateEvent);
    appContainer.addEventListener('keydown', delegateEvent);

    msgList = loadMsgs() || [];
    renderLocalFiles(msgList);

}

function delegateEvent(evtObj) {

    if (evtObj.type == 'click' && evtObj.target.id == 'btn-nick') {
        onNameEditButton(evtObj);
    }

    if ((evtObj.type == 'click') && evtObj.target.id == 'btn-input') {
        onInputButton(evtObj);
    }

    if (evtObj.keyCode == '13') {
        onInputButton(evtObj);
    }

    if (evtObj.type == 'click' && evtObj.target.id.substring(0, 8) == 'btn-edit') {
        onEditMessage(evtObj.target.id.substring(8), evtObj);
    }

    if (evtObj.type == 'click' && evtObj.target.id.substring(0, 10) == 'btn-delete') {
        onDeleteMessage(evtObj.target.id.substring(10), evtObj);
    }

}

function onNameEditButton() {
    var b;
    b = document.getElementById('nick-input').value;
    document.getElementById('nick').innerHTML = b;
}

function onInputButton() {

    var text = document.getElementById('message-text').value;
    var author = document.getElementById('nick').innerHTML;

    if (text.length > 0) {
        var message = createMessage(author, text);
        var msgArea = document.getElementById('message-area');
        msgArea.appendChild(message);
    }
    else {
        alert("You can't send nothing!");
    }
    document.getElementById('message-text').value = '';
}

function renderLocalFiles(list) {
    for (var i = 0; i < list.length; i++) {
        renderMsg(list[i]);
    }
}

function renderMsg(element) {

    var divItem = document.createElement('div');
    divItem.classList.add('message_box');
    divItem.classList.add('your_msg');
    divItem.id = 'd' + element.ide;
    var msgContent = document.createElement('span');
    msgContent.classList.add('message-style');
    msgContent.id = element.ide;

    msgContent.innerHTML = element.author + ': ' + element.message;
    divItem.appendChild(msgContent);
    var buttonEdit = createButton('edit', 'btn-edit' + element.ide);
    var buttonDelete = createButton('delete', 'btn-delete' + element.ide);
    divItem.appendChild(buttonDelete);
    divItem.appendChild(buttonEdit);
    if (element.edited) {
                var editCheck = document.createElement('span');
        editCheck.classList.add('sp');
        editCheck.id = 'sp' + element.ide;
        editCheck.innerHTML = 'edited';
        divItem.appendChild(editCheck);
    
    }
    if (element.deleted) {
        msgContent.innerHTML = "/message deleted/";
        if (editCheck) {
            divItem.removeChild(editCheck);
        }
        divItem.removeChild(buttonDelete);
        divItem.removeChild(buttonEdit);
    }

    document.getElementById('message-area').appendChild(divItem);

}

function createButton(name, clName) {
    var button = document.createElement('button');
    button.classList.add('b');
    button.classList.add('pull-right');
    button.id = clName;
    button.innerHTML = name;
    return button;
}

function createMessage(author, content) {

    var msgId = uniqueId();
    var divItem = document.createElement('div');
    divItem.classList.add('message_box');
    divItem.classList.add('your_msg');
    divItem.id = 'd' + msgId;
    var msgContent = document.createElement('span');
    msgContent.classList.add('message-style');
    msgContent.id = msgId;
    msgContent.innerHTML = author + ': ' + content;
    divItem.appendChild(msgContent);
    var buttonEdit = createButton('edit', 'btn-edit' + msgId);
    var buttonDelete = createButton('delete', 'btn-delete' + msgId);
    divItem.appendChild(buttonDelete);
    divItem.appendChild(buttonEdit);
    var msg = msgLocal(content, author, msgId);
    msgList.push(msg);
    saveMsgs(msgList);
    return divItem;
}


function onDeleteMessage(messageID) {
    var check = confirm("You really want to delete this message?");
    if (check) {

        document.getElementById(messageID).innerHTML = "/message deleted/";
        document.getElementById('d' + messageID).removeChild(document.getElementById('btn-edit' + messageID));
        document.getElementById('d' + messageID).removeChild(document.getElementById('btn-delete' + messageID));
        if (document.getElementById('sp' + messageID) != null) {
            //var divItem = document.getElementById('d' + msgId);
            document.getElementById('d' + messageID).removeChild(document.getElementById('sp' + messageID));
        }
        var index = findByElement(document.getElementById(messageID), msgList);
        var msg = msgList[index];
        msg.deleted = !msg.deleted;
    }
    saveMsgs(msgList);
}

function onEditMessage(messageID) {

    var check = confirm('Do you really want to edit this message?');
    if (check) {

        var newMsg = prompt("Enter new message", '');

        var author = document.getElementById('nick').innerHTML;
        var editCheck = document.createElement('span');
        editCheck.classList.add('sp');
        editCheck.id = 'sp' + messageID;
        editCheck.innerHTML = 'edited';
        document.getElementById('d' + messageID).appendChild(editCheck);
        var index = findByElement(document.getElementById(messageID), msgList);
        var msg = msgList[index];
        if(msg.edited == false){
        msg.edited = !msg.edited;
    }
    else{
        document.getElementById('d' + messageID).removeChild(editCheck);

    }
        msg.message = newMsg;        
        document.getElementById(messageID).innerHTML = msg.author + ": " + newMsg;

    }
    saveMsgs(msgList);
}

function saveMsgs(listSave) {
    if (typeof(Storage) == "undefined") {
        alert("cant access localStorage");
        return;
    }
    localStorage.setItem("MessageList", JSON.stringify(listSave));
}

function loadMsgs() {
    if (typeof(Storage) == "undefined") {
        alert("cant access localStorage");
        return;
    }
    var item = localStorage.getItem("MessageList");
    return item && JSON.parse(item);

}

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();
    return Math.floor(date * random).toString();
}

function findByElement(element, msgs) {
    var id = element.id;
    return findIndex(msgs, id);
}

function findIndex(list, temp) {
    var n = 0;
    while (list[n].ide != temp) {
        n++;
    }
    return n;
}

function msgLocal(text, auth, ID, edited, deleted) {
    return {
        message: text,
        author: auth,
        ide: ID,
        edited: !!edited,
        deleted: !!deleted
    }
}





