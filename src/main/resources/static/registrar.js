
    document.getElementById("registerForm").addEventListener("submit", function (event) {
      event.preventDefault();

      const login = document.getElementById("login").value;
      const password = document.getElementById("password").value;

      const data = {
        login: login,
        password: password,
        role: "USER" // Sempre cria com role USER
      };

      const xhr = new XMLHttpRequest();
      xhr.open("POST", "http://localhost:8080/auth/register", true);
      xhr.setRequestHeader("Content-Type", "application/json");

      xhr.onload = function () {
        if (xhr.status === 200) {
          alert("Cadastro realizado com sucesso! Agora você pode fazer login.");
          window.location.href = "login.html";
        } else {
          alert("Erro no cadastro: " + xhr.responseText);
        }
      };

      xhr.onerror = function () {
        alert("Erro de rede ou CORS.");
      };

      xhr.send(JSON.stringify(data));
    });