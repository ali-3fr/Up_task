function run() {

    var appContainer = document.getElementById('chat-container');
    appContainer.addEventListener('click', delegateEvent);

}

function delegateEvent(evtObj) {

    if (evtObj.type == 'click' && evtObj.target.id == 'btn-nick') {
        onNameEditButton(evtObj);
    }

    if ((evtObj.type == 'click') && evtObj.target.id == 'btn-input') {
        onInputButton(evtObj);
    }

    if(evtObj.type == 'click' && evtObj.target.id.substring(0,8) == 'btn-edit'){
        onEditMessage(evtObj.target.id.substring(8), evtObj);
    }

    if(evtObj.type == 'click' && evtObj.target.id.substring(0,10) == 'btn-delete'){
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

    if(text.length > 0){
        var message = createMessage(author,text);
        var msgArea = document.getElementById('message-area');
        msgArea.appendChild(message);
    }
    else{
        alert("You can't send nothing!");
    }
}

function createButton(name, clName){
    var button = document.createElement('button');
    button.classList.add('b');
    button.classList.add('pull-right');
    button.id = clName;
    button.innerHTML = name;
    return button;
}

function createMessage(author, content){

    var msgId = uniqueId();
    var divItem = document.createElement('div');
    divItem.classList.add('message_box');
    divItem.classList.add('your_msg');
    divItem.id = 'd' + msgId;
    var msgContent = document.createElement('u');
    msgContent.id = msgId;
    msgContent.innerHTML = author + ': ' + content;
    divItem.appendChild(msgContent);
    var buttonEdit = createButton('edit', 'btn-edit' + msgId);
    var buttonDelete = createButton('delete', 'btn-delete' + msgId);
    divItem.appendChild(buttonDelete);
    divItem.appendChild(buttonEdit);

    return divItem;

}

function onDeleteMessage(messageID){
    var check = confirm("You really want to delete this message?");
    if(check){
        document.getElementById(messageID).innerHTML = "/message deleted/";
        if(document.getElementById('sp' + messageID).innerHTML == 'edited'){
            //var divItem = document.getElementById('d' + msgId);
            document.getElementById('d' + messageID).removeChild(document.getElementById('sp' + messageID));
        }
    }
}

function onEditMessage(messageID){

    var check = confirm('Do you really want to edit this message?');
    if(check){
        var newMsg = prompt("Enter new message", '');
        var author = document.getElementById('nick').innerHTML;
        document.getElementById(messageID).innerHTML = author + ": " + newMsg;
        var editCheck = document.createElement('span');
        editCheck.classList.add('sp');
        editCheck.id = 'sp' + messageID;
        editCheck.innerHTML = 'edited';
        document.getElementById('d'+messageID).appendChild(editCheck);
    }

}

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();
    return Math.floor(date * random).toString();
}




