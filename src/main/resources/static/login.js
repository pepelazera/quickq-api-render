/* Código de Lógica da Tela de Login e Cadastro */

document.addEventListener("DOMContentLoaded", function() {
    console.log("Login.js carregado com sucesso!");

    // 1. PEGAR OS ELEMENTOS DA TELA
    const telaLogin = document.getElementById("tela-login");
    const telaCadastro = document.getElementById("tela-cadastro");
    const btnIrCadastro = document.getElementById("btn-ir-cadastro");
    const btnIrLogin = document.getElementById("btn-ir-login");

    // Verifica se os elementos existem antes de tentar usar
    if (!telaLogin || !telaCadastro || !btnIrCadastro || !btnIrLogin) {
        console.error("Erro: Elementos da tela não encontrados.");
        return;
    }

    // 2. FUNÇÃO: IR PARA CADASTRO (Esconde Login -> Mostra Cadastro)
    btnIrCadastro.addEventListener("click", function(e) {
        e.preventDefault(); 
        console.log("Clicou em Criar Conta");
        telaLogin.style.display = "none";     
        telaCadastro.style.display = "block"; 
    });

    // 3. FUNÇÃO: VOLTAR PARA LOGIN (Esconde Cadastro -> Mostra Login)
    btnIrLogin.addEventListener("click", function(e) {
        e.preventDefault();
        console.log("Clicou em Voltar para Login");
        telaCadastro.style.display = "none"; 
        telaLogin.style.display = "block";   
    });

    // ==========================================
    //  LÓGICA DE LOGIN (ENTRAR)
    // ==========================================
    const formLogin = document.getElementById("login-form");
    if (formLogin) {
        formLogin.addEventListener("submit", function(e) {
            e.preventDefault();
            const email = document.getElementById("login-email").value;
            const senha = document.getElementById("login-senha").value;
            const msgErro = document.getElementById("msg-erro-login");

            msgErro.style.display = "none";
            msgErro.innerText = "";

            console.log("Tentando login...");

            fetch('/api/usuarios/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email: email, senha: senha })
            })
            .then(response => {
                if (response.ok) return response.json();
                throw new Error("Email ou senha inválidos");
            })
            .then(data => {
                console.log("Login Sucesso!", data.usuario);
                localStorage.setItem("token", data.token);
                localStorage.setItem("usuarioLogado", JSON.stringify(data.usuario));
                window.location.href = "index2.html";
            })
            .catch(error => {
                console.error("Erro login:", error);
                msgErro.innerText = error.message;
                msgErro.style.display = "block";
            });
        });
    }

    // ==========================================
    //  LÓGICA DE CADASTRO (CRIAR CONTA)
    // ==========================================
    const formCadastro = document.getElementById("cadastro-form");
    if (formCadastro) {
        formCadastro.addEventListener("submit", function(e) {
            e.preventDefault();

            const dadosCadastro = {
                nome: document.getElementById("cad-nome").value,
                email: document.getElementById("cad-email").value,
                perfil: document.getElementById("cad-perfil").value,
                hashSenha: document.getElementById("cad-senha").value,
                active: true
            };
            
            const msgErro = document.getElementById("msg-erro-cadastro");
            msgErro.style.display = "none";
            msgErro.innerText = "";

            console.log("Tentando cadastrar...", dadosCadastro);

            fetch('/api/usuarios/salvar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dadosCadastro)
            })
            .then(response => {
                if (response.ok) return response.json();
                throw new Error("Erro ao cadastrar. Verifique se o email já existe.");
            })
            .then(usuario => {
                alert("Conta criada com sucesso! Faça login com seu e-mail e senha.");
                telaCadastro.style.display = "none";
                telaLogin.style.display = "block";
                document.getElementById("login-email").value = usuario.email;
            })
            .catch(error => {
                console.error("Erro cadastro:", error);
                msgErro.innerText = error.message;
                msgErro.style.display = "block";
            });
        });
    }

});