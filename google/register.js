const chat = document.getElementById("chat");
const userInput = document.getElementById("userInput");

let step = "username";
let username, password, confirmPassword, email;

userInput.addEventListener("keydown", function (event) {
  if (event.key === "Enter") {
    event.preventDefault();

    const userMessage = document.createElement("div");
    userMessage.className = "message user";
    userMessage.textContent = userInput.value;
    chat.appendChild(userMessage);

    switch (step) {
      case "username":
        username = userInput.value;
        checkUsernameAvailability(username);
        break;
      case "password":
        password = userInput.value;
        askForConfirmPassword();
        break;
      case "confirmPassword":
        confirmPassword = userInput.value;
        if (confirmPassword === password) {
          askForEmail();
        } else {
          showPasswordMismatch();
        }
        break;
      case "email":
        email = userInput.value;
        registerUser();
        break;
    }

    userInput.value = "";
  }
});

function askForPassword() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请设置您的密码：";
  chat.appendChild(botMessage);
  step = "password";
}

function askForConfirmPassword() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请再次输入您的密码以确认：";
  chat.appendChild(botMessage);
  step = "confirmPassword";
}

function showPasswordMismatch() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "两次输入的密码不一致，请重新设置您的密码：";
  chat.appendChild(botMessage);
  step = "password";
}

function askForEmail() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请输入您的电子邮箱：";
  chat.appendChild(botMessage);
  step = "email";
}

function registerUser() {
  // 在这里，您可以将用户提供的信息发送给服务器以完成注册过程。
  // 您可以参考 loginUser 函数中的实现来完成此部分。
  console.log("注册信息：", {
    username: username,
    password: password,
    email: email,
  });
}
function checkUsernameAvailability(username) {
  // 设置查询用户名是否存在的服务器端接口地址
  const url = "https://your_server_url/api/check_username";

  // 向服务器发送用户名查询请求
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username }),
  })
    .then((response) => response.json())
    .then((data) => {
      // 根据服务器返回的状态值判断用户名是否可用
      if (data.state === 0) {
        // 用户名可用，继续注册流程
        askForPassword();
      } else {
        // 用户名已存在，提示用户并返回到输入用户名的步骤
        showUsernameExists();
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function showUsernameExists() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "用户名已存在，请尝试其他用户名：";
  chat.appendChild(botMessage);
  step = "username";
}
