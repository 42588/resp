document.addEventListener("DOMContentLoaded", function () {
  fillCurrentUrl();
  addFormSubmitListener();
  document
    .getElementById("goto-options")
    .addEventListener("click", openOptionsModal);
  document.querySelector(".close").addEventListener("click", closeModal);
});

function fillCurrentUrl() {
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    var currentTab = tabs[0];
    document.getElementById("url-input").value = currentTab.url;
  });
}

function addFormSubmitListener() {
  document
    .getElementById("myForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      submitData();
    });
}

function submitData() {
  var username = document.getElementById("username-input").value;
  var password = document.getElementById("password-input").value;
  var url = document.getElementById("url-input").value;

  var data = {
    userName: username,
    password: password,
    website: url,
  };

  var xhr = new XMLHttpRequest();
  // alert(data.password,data.username.data.);
  xhr.open("POST", "http://localhost:8080/save", true);
  xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      document.getElementById("message").innerHTML = "数据提交成功！";
    } else if (xhr.readyState === 4) {
      document.getElementById("message").innerHTML = "数据提交失败！";
    }
  };
  xhr.send(JSON.stringify(data));
}

function openOptionsModal() {
  document.getElementById("optionsModal").style.display = "block";
  getUserData();
}

function closeModal() {
  document.getElementById("optionsModal").style.display = "none";
}

function getUserData() {
  var userDataUrl = "http://localhost:8080/userdata";

  var xhr = new XMLHttpRequest();
  xhr.open("GET", userDataUrl, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      var userData = JSON.parse(xhr.responseText);
      displayUserData(userData);
    }
  };
  xhr.send();
}

function displayUserData(userData) {
  var container = document.getElementById("user-data");
  container.innerHTML = "";

  userData.forEach(function (item) {
    var itemDiv = document.createElement("div");
    itemDiv.innerHTML = `
      <p>用户名: ${item.userName}</p>
      <p>密码: ${item.password}</p>
      <p>网址: ${item.website}</p>
      <button class="edit">编辑</button>
      <button class="delete">删除</button>
      <button class="copy">复制</button>
      <hr>
    `;
    container.appendChild(itemDiv);

    itemDiv.querySelector(".edit").addEventListener("click", function () {
      editItem(item);
    });

    itemDiv.querySelector(".delete").addEventListener("click", function () {
      deleteItem(item);
    });

    itemDiv.querySelector(".copy").addEventListener("click", function () {
      copyItem(item);
    });
  });
}

function editItem(item) {}

function deleteItem(item) {}

function copyItem(item) {
  var dummy = document.createElement("textarea");
  document.body.appendChild(dummy);
  dummy.value = `用户名: ${item.username}\n密码: ${item.password}\n网址: ${item.url}`;
  dummy.select();
  document.execCommand("copy");
  document.body.removeChild(dummy);

  alert("数据已复制到剪贴板");
}
