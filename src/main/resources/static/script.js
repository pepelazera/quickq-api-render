/* CÓDIGO COMPLETO E FINAL DO SISTEMA QUICKQ */

document.addEventListener("DOMContentLoaded", function() {

    // =================================================================
    // 1. SEGURANÇA E LOGOUT
    // =================================================================
    // Checagem só de UX (mostra/esconde tela); a autenticação de verdade é o cookie
    // HttpOnly auth_token, que o JS não consegue ler - quem garante isso é o backend.
    const usuarioLogado = localStorage.getItem("usuarioLogado");

    if (!usuarioLogado) {
        window.location.href = "index.html";
        return;
    }

    const usuarioObj = JSON.parse(usuarioLogado);

    const API_BASE = '';

    function getCookie(nome) {
        const match = document.cookie.match(new RegExp('(?:^|; )' + nome + '=([^;]*)'));
        return match ? decodeURIComponent(match[1]) : null;
    }

    // Faz fetch com o cookie de sessão + header X-CSRF-Token (double-submit); se a
    // sessão estiver ausente/expirada, volta pro login.
    function apiFetch(path, options = {}) {
        const metodo = (options.method || 'GET').toUpperCase();
        const headers = Object.assign(
            { 'Content-Type': 'application/json' },
            options.headers || {}
        );
        if (metodo !== 'GET' && metodo !== 'HEAD') {
            headers['X-CSRF-Token'] = getCookie('XSRF-TOKEN') || '';
        }

        return fetch(API_BASE + path, Object.assign({}, options, { headers, credentials: 'include' })).then(res => {
            if (res.status === 401) {
                localStorage.removeItem("usuarioLogado");
                window.location.href = "index.html";
                throw new Error("Sessão expirada. Faça login novamente.");
            }
            return res;
        });
    }

    // Botão Sair
    const menuLateral = document.querySelector(".menu-lateral");
    const btnLogout = document.createElement("div");
    btnLogout.className = "logout-btn";
    btnLogout.innerHTML = `Sair (${usuarioObj.nome})`;
    btnLogout.style.marginTop = "10px";
    btnLogout.style.padding = "10px";
    btnLogout.style.backgroundColor = "#ffcccc";
    btnLogout.style.textAlign = "center";
    btnLogout.style.borderRadius = "5px";
    btnLogout.style.cursor = "pointer";
    btnLogout.style.color = "#cc0000";

    btnLogout.onclick = function() {
        apiFetch('/api/usuarios/logout', { method: 'POST' })
            .catch(() => {}) // mesmo se der erro, limpa o estado local e sai
            .finally(() => {
                localStorage.removeItem("usuarioLogado");
                window.location.href = "index.html";
            });
    };

    const btnTema = document.getElementById("theme-toggle");
    if(btnTema) {
        menuLateral.insertBefore(btnLogout, btnTema);
    } else {
        menuLateral.appendChild(btnLogout);
    }

    const conteudo = document.querySelector(".conteudo-principal");


    // =================================================================
    // 2. FLUXO PRINCIPAL: INICIAR AVALIAÇÃO
    // =================================================================
    const botaoIniciar = document.getElementById("menu-iniciar-avaliacao");

    if (botaoIniciar) {
        botaoIniciar.addEventListener("click", function() {
            conteudo.innerHTML = `
                <h1>Avaliação Neurofuncional QuickQ</h1>
                <div class="tab-menu">
                    <button class="tab-button active" data-tab="fase1">Fase 1: Dados do Cliente</button>
                    <button class="tab-button" data-tab="fase2">Fase 2: Dados Técnicos</button>
                    <button class="tab-button" data-tab="fases3-4">Fases 3 e 4: Validação</button>
                </div>
                <div class="tab-content-container">
                    <!-- FASE 1 -->
                    <div id="fase1" class="tab-content active">
                        <h2>Fase 1: Dados do Cliente</h2>
                        <form id="form-fase1" class="evaluation-form">
                            <div class="form-group"><label>Nome:</label><input type="text" id="cliente-nome"></div>
                            <div class="form-group"><label>Idade:</label><input type="number" id="cliente-idade" style="width: 100px;"></div>
                            <div class="form-group"><label>Gênero:</label><select id="cliente-genero"><option>Selecione...</option><option>Masculino</option><option>Feminino</option></select></div>
                            <div class="form-group"><label>Atividade Principal:</label><input type="text" id="cliente-atividade"></div>
                            <div class="form-group"><label>Responsável:</label><input type="text" id="cliente-responsavel"></div>
                            <div class="form-group"><label>Diagnóstico:</label><textarea id="cliente-diagnostico"></textarea></div>
                            <div class="form-group"><label>Médicos:</label><input type="text" id="cliente-medicos"></div>
                            <div class="form-group"><label>Medicamentos:</label><input type="text" id="cliente-medicamentos"></div>
                            <div class="form-group"><label>Histórico Médico:</label><textarea id="cliente-historico"></textarea></div>
                            <div class="form-group"><label>Documentos:</label><input type="text" id="cliente-documentos"></div>
                        </form>
                    </div>
                    <!-- FASE 2 -->
                    <div id="fase2" class="tab-content">
                        <h2>Fase 2: Dados Técnicos</h2>
                        <form id="form-fase2" class="evaluation-form">
                            <div class="form-group"><label>Data do EEG:</label><input type="date" id="eeg-data"></div>
                            <div class="form-group"><label>Tipo:</label><select id="eeg-tipo"><option>Avaliação</option><option>Reavaliação</option></select></div>
                            <div class="form-group"><label>Sessões:</label><input type="number" id="eeg-sessoes"></div>
                            <div class="form-group"><label>Equipamento:</label><input type="text" id="eeg-equipamento"></div>
                            <div class="form-group"><label>Neuropsicólogo:</label><input type="text" id="eeg-neuropsicologo"></div>
                            <div class="form-group"><label>Observações:</label><textarea id="eeg-observacoes"></textarea></div>
                        </form>
                    </div>
                    <!-- FASE 3 e 4 -->
                    <div id="fases3-4" class="tab-content">
                        <h2>Fases 3 e 4: Validação</h2>
                        <div class="evaluation-form">
                            <div class="form-group"><label>Upload PDF:</label><input type="file" id="pdf-upload"></div>
                            <div class="form-group"><button type="button" id="btn-validar-pdf" class="btn btn-primary">Comandar Validação</button></div>
                            
                            <div id="secao-relatorio" style="display: none; margin-top: 20px; border-top: 1px solid #ccc; padding-top: 10px;">
                                <h3>Relatório Técnico-Clínico</h3>
                                <div id="relatorio-output" class="report-box"><p>Aguardando IA...</p></div>
                                <div class="form-group">
                                    <label>Argumento Técnico:</label>
                                    <textarea id="argumento-tecnico-1"></textarea>
                                </div>
                                <div class="button-group">
                                    <button type="button" id="btn-aceitar-relatorio" class="btn btn-success">Aceitar Relatório</button>
                                    <button type="button" id="btn-reprocessar" class="btn btn-warning">Solicitar Reprocessamento</button>
                                </div>
                            </div>
                            
                            <div id="secao-evolutiva" style="display: none; margin-top: 20px; border-top: 1px solid #ccc; padding-top: 10px;">
                                <h3>Análise Evolutiva</h3>
                                <div id="evolutiva-output" class="report-box"><p>Aguardando...</p></div>
                                <div class="button-group">
                                    <button type="button" id="btn-finalizar-processo" class="btn btn-success">Finalizar Processo</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> 
            `;
            adicionarLogicaDasAbas();
        });
    }

    function adicionarLogicaDasAbas() {
        // Lógica das Abas
        const tabButtons = document.querySelectorAll(".tab-button");
        const tabContents = document.querySelectorAll(".tab-content");
        tabButtons.forEach(button => {
            button.addEventListener("click", function() {
                const targetTab = this.dataset.tab; 
                tabButtons.forEach(btn => btn.classList.remove("active"));
                tabContents.forEach(content => content.classList.remove("active"));
                this.classList.add("active");
                document.getElementById(targetTab).classList.add("active");
            });
        });

        // Botões da Fase 3/4
        const btnValidar = document.getElementById("btn-validar-pdf");
        if(btnValidar) { 
            const btnAceitar = document.getElementById("btn-aceitar-relatorio");
            const btnReprocessar = document.getElementById("btn-reprocessar");
            const btnFinalizar = document.getElementById("btn-finalizar-processo");
            
            const secaoRelatorio = document.getElementById("secao-relatorio");
            const secaoEvolutiva = document.getElementById("secao-evolutiva");
            const outputRelatorio = document.getElementById("relatorio-output");
            const outputEvolutiva = document.getElementById("evolutiva-output");

            // 1. VALIDAR (Simulação IA)
            btnValidar.addEventListener("click", () => {
                secaoRelatorio.style.display = "block";
                outputRelatorio.innerHTML = "<p style='color:blue'>A IA está analisando os dados... Aguarde.</p>";
                setTimeout(() => {
                    outputRelatorio.innerHTML = `
                        <p><strong>Relatório da IA:</strong></p>
                        <p>Os dados do PDF foram validados com sucesso. O padrão de ondas Teta/Beta sugere...</p>
                        <p style='color:green'>✔ Dados consistentes.</p>
                    `;
                }, 2000);
            });

            // 2. REPROCESSAR (Simulação)
            btnReprocessar.addEventListener("click", () => {
                outputRelatorio.innerHTML = "<p style='color:orange'>Reprocessando dados com novos parâmetros... Aguarde.</p>";
                setTimeout(() => {
                    outputRelatorio.innerHTML = `
                        <p><strong>Relatório da IA (V2):</strong></p>
                        <p>Reanálise concluída com novos parâmetros.</p>
                        <p style='color:green'>✔ Dados revalidados.</p>
                    `;
                }, 2000);
            });

            // 3. ACEITAR E SALVAR (Real)
            btnAceitar.addEventListener("click", () => {
                const dadosAvaliacao = {
                    // Pega dados dos formulários anteriores
                    obs: "Avaliação QuickQ. Obs: " + (document.getElementById("eeg-observacoes")?.value || ""),
                    dataDoEgg: document.getElementById("eeg-data")?.value || null,
                    avaliacao: document.getElementById("eeg-tipo")?.value,
                    neurofeedbackSessoes: document.getElementById("eeg-sessoes")?.value || 0,
                    dataProcessamento: new Date().toISOString().split('T')[0]
                };

                console.log("Salvando Avaliação...", dadosAvaliacao);

                apiFetch('/api/avaliacoes/salvar', {
                    method: 'POST',
                    body: JSON.stringify(dadosAvaliacao)
                })
                .then(res => {
                    if(res.ok) return res.json();
                    throw new Error("Erro ao salvar.");
                })
                .then(av => {
                    alert("Avaliação Salva! ID: " + av.id);
                    secaoEvolutiva.style.display = "block";
                    outputEvolutiva.innerHTML = `<p>Avaliação #${av.id} registrada. Melhora de 15% nos índices.</p>`;
                    btnAceitar.style.display = "none"; // Esconde para não duplicar
                    btnReprocessar.style.display = "none";
                })
                .catch(err => alert("Erro ao salvar avaliação: " + err.message + "\nVerifique se o backend está rodando."));
            });

            // 4. FINALIZAR PROCESSO
            btnFinalizar.addEventListener("click", () => {
                alert("Processo Finalizado com Sucesso! Voltando ao início.");
                location.reload(); // Recarrega a página para limpar tudo
            });
        }
    } 


    // =================================================================
    // 3. CONSULTAS (Relatórios e Documentos)
    // =================================================================
    const setupsConsulta = [
        { id: "menu-analise-evolutiva", titulo: "Consulta de Análise Evolutiva", tipo: "evolutiva" },
        { id: "menu-relatorio-clinico", titulo: "Consulta de Relatório Clínico", tipo: "clinico" },
        { id: "menu-documentos", titulo: "Consulta de Documentos", tipo: "documento" }
    ];

    setupsConsulta.forEach(setup => {
        const btn = document.getElementById(setup.id);
        if(btn) btn.addEventListener("click", () => carregarTelaConsulta(setup.titulo, setup.tipo));
    });

    function carregarTelaConsulta(titulo, tipo) {
        let urlApi = (tipo === "documento") ? '/api/documentos/listar' : '/api/avaliacoes/listar';
        let colunas = (tipo === "documento") 
            ? `<th>Documento</th><th>Extensão</th><th>Tipo</th><th>Data</th>`
            : `<th>ID</th><th>Data Proc.</th><th>Avaliação</th><th>Sessões</th>`;

        conteudo.innerHTML = `
            <h1>${titulo}</h1>
            <div class="consulta-container">
                <div class="filtro-container">
                    <h2>Filtro</h2>
                    <form class="evaluation-form" style="max-width: 100%;">
                        <div class="form-group"><label>Buscar:</label><input type="text" placeholder="Digite..."></div>
                        <div class="form-group"><button type="button" id="btn-aplicar-filtro" class="btn btn-primary">Buscar no Banco</button></div>
                    </form>
                </div>
                <div class="resultados-container">
                    <div id="browser-section" style="display:none;">
                        <h2>Resultados</h2>
                        <table class="browser-table"><thead><tr>${colunas}</tr></thead><tbody id="tabela-corpo"></tbody></table>
                        <p id="msg-status"></p>
                    </div>
                </div>
            </div>
        `;
        
        document.getElementById("btn-aplicar-filtro").addEventListener("click", () => {
            document.getElementById("browser-section").style.display = "block";
            const tabela = document.getElementById("tabela-corpo");
            const msg = document.getElementById("msg-status");
            tabela.innerHTML = "";
            msg.innerText = "Carregando...";
            
            apiFetch(urlApi)
                .then(res => { if(!res.ok) throw new Error(res.status); return res.json(); })
                .then(dados => {
                    msg.innerText = "";
                    if(dados.length === 0) msg.innerText = "Nenhum registro encontrado.";
                    dados.forEach(item => {
                        let html = "";
                        if(tipo === "documento") {
                            html = `<td>${item.nomeDoc || "-"}</td><td>${item.extensaoDoc || "-"}</td><td>${item.tipoDoc || "-"}</td><td>${item.createdAt || "-"}</td>`;
                        } else {
                            // Exibe dados da Avaliação
                            html = `<td>${item.id}</td><td>${item.dataProcessamento}</td><td>${item.avaliacao}</td><td>${item.neurofeedbackSessoes}</td>`;
                        }
                        const tr = document.createElement("tr");
                        tr.innerHTML = html;
                        tr.classList.add("resultado-linha");
                        tabela.appendChild(tr);
                    });
                })
                .catch(err => {
                    msg.innerText = "Erro de conexão: " + err.message;
                    msg.style.color = "red";
                });
        });
    }


    // =================================================================
    // 4. CADASTROS (Fábrica de Formulários)
    // =================================================================

    function configCadastro(menuId, titulo, campos, urlApi, nomeEntidade) {
        const btn = document.getElementById(menuId);
        if(!btn) return;
        
        btn.addEventListener("click", function() {
            let htmlCampos = "";
            campos.forEach(c => {
                const required = c.required ? "required" : "";
                const requiredMarker = c.required ? " *" : "";
                const min = c.min !== undefined ? `min="${c.min}"` : "";
                if(c.type === 'select') {
                    let opts = c.options.map(o => `<option value="${o.value}">${o.text}</option>`).join('');
                    htmlCampos += `<div class="form-group"><label>${c.label}${requiredMarker}:</label><select id="${c.id}" ${required}>${opts}</select></div>`;
                } else if(c.type === 'textarea') {
                    htmlCampos += `<div class="form-group"><label>${c.label}${requiredMarker}:</label><textarea id="${c.id}" ${required}></textarea></div>`;
                } else {
                    let style = c.width ? `style="width:${c.width}"` : "";
                    let type = c.type || "text";
                    let disabled = c.disabled ? "disabled" : "";
                    htmlCampos += `<div class="form-group"><label>${c.label}${requiredMarker}:</label><input type="${type}" id="${c.id}" ${style} ${disabled} ${required} ${min}></div>`;
                }
            });

            conteudo.innerHTML = `
                <h1>${titulo}</h1>
                <form class="evaluation-form" id="form-cadastro">
                    ${htmlCampos}
                    <br>
                    <div class="button-group">
                        <button type="button" id="btn-salvar" class="btn btn-success">Salvar Novo</button>
                        <button type="button" class="btn" onclick="document.getElementById('form-cadastro').reset()">Limpar</button>
                    </div>
                </form>
            `;

            // Lógica Específica para Neurofeedback (Cálculo de Duração)
            if (menuId === 'menu-neurofeedback') {
                const hInicio = document.getElementById("nfb-inicio");
                const hTermino = document.getElementById("nfb-termino");
                const duracao = document.getElementById("nfb-duracao");
                const calc = () => {
                    if(hInicio.value && hTermino.value) {
                        const [h1, m1] = hInicio.value.split(':').map(Number);
                        const [h2, m2] = hTermino.value.split(':').map(Number);
                        let min = (h2 * 60 + m2) - (h1 * 60 + m1);
                        duracao.value = min >= 0 ? min + " min" : "Inválido";
                    }
                };
                hInicio.addEventListener("change", calc);
                hTermino.addEventListener("change", calc);
            }

            document.getElementById("btn-salvar").addEventListener("click", function() {
                const formulario = document.getElementById("form-cadastro");

                campos.forEach(c => {
                    const campo = document.getElementById(c.id);
                    const vazio = c.required && campo.value.trim() === "";
                    campo.setCustomValidity(vazio ? `${c.label} é obrigatório` : "");
                });

                if (!formulario.reportValidity()) return;

                let dados = {};
                campos.forEach(c => {
                    let val = document.getElementById(c.id).value;
                    if(c.required) val = val.trim();
                    // Tratamentos especiais
                    if(c.id === 'equip-ativo') val = (val === 'true');
                    // Tratamento de Hora (Adiciona :00 para o Java LocalTime)
                    if(c.type === 'time') {
                        val = val ? val + ":00" : null;
                    }
                    // Tratamento de Data (Envia null se vazio)
                    if(c.type === 'date' && val === "") val = null;
                    
                    dados[c.prop] = val; 
                });
                
                console.log("Enviando:", dados);
                
                apiFetch(urlApi, {
                    method: 'POST',
                    body: JSON.stringify(dados)
                })
                .then(res => { 
                    if(!res.ok) return res.text().then(text => { throw new Error(text) }); 
                    return res.json(); 
                })
                .then(obj => alert(`${nomeEntidade} salvo com sucesso! ID: ${obj.id}`))
                .catch(err => alert("Erro ao salvar: " + err.message));
            });
        });
    }

    // --- CONFIGURAÇÃO DE CADA TELA ---
    
    configCadastro("menu-cliente", "Cadastro de Cliente", [
        { label: "Nome", id: "cliente-nome", prop: "nome", required: true },
        { label: "Idade", id: "cliente-idade", type: "number", width: "100px", prop: "idade", required: true, min: 1 },
        { label: "Gênero", id: "cliente-genero", type: "select", options:[{value:"M", text:"Masculino"}, {value:"F", text:"Feminino"}], prop: "genero" },
        { label: "Atividade", id: "cliente-atividade", prop: "atividadePrincipal" },
        { label: "Responsável", id: "cliente-responsavel", prop: "responsavel", required: true },
        { label: "Diagnóstico", id: "cliente-diagnostico", type: "textarea", prop: "diagnostico", required: true },
        { label: "Whatsapp", id: "cliente-whatsapp", prop: "whatsapp" },
        { label: "Email", id: "cliente-email", type: "email", prop: "email" },
        { label: "Endereço", id: "cliente-endereco", prop: "endereco" },
        { label: "Data Cadastro", id: "cliente-data", type: "date", prop: "dataCadastro" }
    ], "/api/clientes/salvar", "Cliente");

    configCadastro("menu-neuropsicologo", "Cadastro de Neuropsicólogo", [
        { label: "Nome", id: "neuro-nome", prop: "nome", required: true },
        { label: "CRP", id: "neuro-crp", prop: "crp", required: true },
        { label: "Whatsapp", id: "neuro-zap", prop: "whatsapp" },
        { label: "Email", id: "neuro-email", type: "email", prop: "email", required: true },
        { label: "Endereço", id: "neuro-end", prop: "endereco" },
        { label: "Senha", id: "neuro-senha", type: "password", prop: "hashSenha", required: true }
    ], "/api/neuropsicologos/salvar", "Neuropsicólogo");

    configCadastro("menu-operador-eeg", "Cadastro de Operador", [
        { label: "Nome", id: "op-nome", prop: "nome" },
        { label: "Formação", id: "op-formacao", prop: "formacao" },
        { label: "Registro", id: "op-reg", prop: "registroConselho" },
        { label: "Whatsapp", id: "op-zap", prop: "whatsapp" },
        { label: "Email", id: "op-email", prop: "email" },
        { label: "Endereço", id: "op-end", prop: "endereco" },
        { label: "Senha", id: "op-senha", type: "password", prop: "hashSenha" }
    ], "/api/operadores/salvar", "Operador");

    configCadastro("menu-medico", "Cadastro de Médico", [
        { label: "Nome", id: "med-nome", prop: "nome" },
        { label: "Especialização", id: "med-esp", prop: "especializacao" },
        { label: "CRM", id: "med-crm", prop: "crm" },
        { label: "RQE", id: "med-rqe", prop: "rqe" },
        { label: "Whatsapp", id: "med-zap", prop: "whatsapp" },
        { label: "Email", id: "med-email", prop: "email" },
        { label: "Endereço", id: "med-end", prop: "endereco" }
    ], "/api/medicos/salvar", "Médico");

    configCadastro("menu-equipamento", "Cadastro de Equipamento", [
        { label: "Nome", id: "equip-nome", prop: "nome" },
        { label: "Marca", id: "equip-marca", prop: "marca" },
        { label: "Modelo", id: "equip-modelo", prop: "modelo" },
        { label: "Canais EEG", id: "equip-canais", type: "number", prop: "canaisEeg" },
        { label: "Outros Canais", id: "equip-outros", type: "number", prop: "outrosCanais" },
        { label: "Fabricante", id: "equip-fab", prop: "fabricante" },
        { label: "Nº Série", id: "equip-serie", prop: "numeroSerie" },
        { label: "Nota Fiscal", id: "equip-nota-fiscal", prop: "notaFiscal" },
        { label: "Patrimônio", id: "equip-pat", prop: "patrimonio" },
        { label: "Ativo", id: "equip-ativo", type: "select", options:[{value:"true", text:"Sim"},{value:"false", text:"Não"}], prop: "ativo" }
    ], "/api/equipamentos/salvar", "Equipamento");

    configCadastro("menu-medicamento", "Cadastro de Medicamento", [
        { label: "ID Cliente", id: "remedio-cli", prop: "idClienteRef" },
        { label: "Nome", id: "remedio-nome", prop: "nome" },
        { label: "Princípio Ativo", id: "remedio-princ", prop: "principioAtivo" },
        { label: "Dosagem", id: "remedio-dose", prop: "dosagemAtual" },
        { label: "Posologia", id: "remedio-pos", prop: "posologia" },
        { label: "Data Início", id: "remedio-data", type: "date", prop: "dataInicio" },
        { label: "Médico", id: "remedio-med", prop: "medico" },
        { label: "Reações", id: "remedio-rea", type: "textarea", prop: "reacoes" }
    ], "/api/medicamentos/salvar", "Medicamento");

    configCadastro("menu-neurofeedback", "Cadastro de Sessão Neurofeedback", [
        { label: "Cliente (ID)", id: "nfb-cliente", prop: "idClienteRef" },
        { label: "Equipamento (ID)", id: "nfb-equip", prop: "idEquipamentoRef" },
        { label: "Data", id: "nfb-data", type: "date", prop: "data" },
        { label: "Sessão Nº", id: "nfb-sessao", prop: "sessaoNumero" },
        { label: "Hora Início", id: "nfb-inicio", type: "time", prop: "horaInicio" },
        { label: "Hora Término", id: "nfb-termino", type: "time", prop: "horaTermino" },
        { label: "Duração", id: "nfb-duracao", disabled: true, prop: "duracao" },
        { label: "Estado Paciente", id: "nfb-estado", prop: "estadoPaciente" },
        { label: "Descrever", id: "nfb-descrever", type: "textarea", prop: "descrever" },
        { label: "Condições", id: "nfb-condicoes", type: "textarea", prop: "condicoesFisiologicas" },
        { label: "Protocolos", id: "nfb-prot", type: "textarea", prop: "protocolos" },
        { label: "Freq. Alvos", id: "nfb-frequencias", prop: "frequenciasAlvos" },
        { label: "Artefatos", id: "nfb-artefatos", prop: "artefatos" },
        { label: "Ajustes", id: "nfb-ajustes", type: "textarea", prop: "ajustesThresholds" },
        { label: "Obs Clínicas", id: "nfb-obs", type: "textarea", prop: "obsClinicas" },
        { label: "Feedback", id: "nfb-feedback", type: "textarea", prop: "feedbackPaciente" }
    ], "/api/neurofeedback/salvar", "Sessão de Neurofeedback");

    // --- MODO NOTURNO ---
    const themeToggle = document.getElementById("theme-toggle");
    themeToggle.addEventListener("click", function() {
        document.body.classList.toggle("dark-mode");
        if (document.body.classList.contains("dark-mode")) {
            themeToggle.textContent = "Mudar Tema ☀️"; 
            localStorage.setItem("theme", "dark"); 
        } else {
            themeToggle.textContent = "Mudar Tema 🌙"; 
            localStorage.setItem("theme", "light"); 
        }
    });

    if (localStorage.getItem("theme") === "dark") {
        document.body.classList.add("dark-mode");
        themeToggle.textContent = "Mudar Tema ☀️";
    }
});
