var bmauth_translations = {};

bmauth_translations.en = {

	// Common text
	"btn.ok": "OK",
	"btn.cancel": "Cancelar",
	"btn.save": "Salvar",
	"btn.send": "Enviar",
	"btn.new": "Novo",
	"text.yes": "Sim",
	"text.no": "Não",
	
	// Field errors
	"fieldErrors": {
		"message": "O campo {{field}} {{message}}.",
		"NotBlank": "não pode estar em branco",
		"NotNull": "não pode ser nulo",
		"email": "Email inválido.",
		"required": "Campo obrigatório.",
		"bmauthPasswordMatch": "As senhas não coindicem."
			
	},

	// Remove modals
	"remove_modal": {
		"header.remove": "Remover",
		"text.are_you_sure": "Tem certeza que deseja remover <strong>{{name}}</strong> (ID: {{id}})?"
	},

	// Login screen
	"login": {
		"header": "Entre com o seu login.",
		"header.or": "Ou use:",
		"error.loginError": "Nome ou senha incorretos.",
		"error.social.loginError": "Erro interno ao fazer login.",
		"error.authorizationError": "Usuário não autorizado no módulo administrativo.",
		"btn.facebook.signin": "Entre com o Facebook",
		"btn.google.signin": "Entre com o Google",
		"btn.login": "Login",
		"btn.register": "Criar cadastro",
		"form.placeholder.email": "@:signup.label.email",
		"form.placeholder.password": "@:signup.label.password",
		"form.label.user": "Usuário",
		"form.label.password": "Senha",
		"form.label.remember": "Lembrar de mim",
		"form.label.forgot_password": "Esqueci minha senha",
		"form.msg.forgotMyPasswordCreated": "Procedimento enviado para seu e-mail"
	},
	
	// Signup screen
	"signup": {
		"header": "Cadastro",
		"header.success": "Usuário criado",
		"label.name": "Nome",
		"label.document_type": "CPF ou CNPJ",
		"label.email": "E-mail",
		"label.password": "Senha",
		"label.repeat_password": "Digite a senha novamente",
		"btn.register": "Cadastrar"
	},
	
	// Applications screens
	"application": {
		"header.delete.success": "Aplicação removida.",
		"header.save.success": "Aplicação salva.",
		"btn.add": "Nova aplicação",
		
		"form.header": "Edição Aplicativo",
		"form.label.id": "ID",
		"form.label.name": "Nome da aplicação",
		"form.label.url": "URL",
		"form.label.test_mode": "Modo de teste",
		"form.label.mandatory_contract": "Contrato obrigatório",
		"form.label.active": "Ativo",

		"role.form.header": "Roles",
		"role.form.column.id": "Id",
		"role.form.column.name": "Nome",
		"role.form.header.delete.success" : "Role removida",
		"role.form.header.save.success" : "Role adicionada",

		"contract.form.header": "Contratos",
		"contract.form.column.Id": "Id",
		"contract.form.column.version": "Versão",
		"contract.form.column.creation_date": "Criado em",
		"contract.form.header.delete.success" : "Contrato removido",
		"contract.form.header.save.success" : "Contrato salvo"
	},
	
	// edit Role modal
	"edit_role_modal": {
		"role.form.header": "Edição de Role",
		"role.form.label.id": "Id",
		"role.form.label.name": "Nome"
	},

	// edit Contract modal
	"edit_contract_modal": {
		"contract.form.header": "Edição de Contrato",
		"contract.form.label.id": "Id",
		"contract.form.label.contractVersion": "Versão",
		"contract.form.label.description": "Descrição",
		"contract.form.label.htmlContract": "Contrato HTML",
		"contract.form.label.language": "Idioma"
	},
	
	// Users screens
	"user": {
		"header.delete.success": "Usuário removido.",
		"btn.add": "Novo usuário",
		"form.label.id": "ID",
		"form.label.name": "Nome",
		"form.label.email": "Email",
		"form.label.type": "Tipo",
		"form.label.active": "Ativo"
	},
	
	"forgotPassword": { 
		"form.header": "Reset sua senha",
		"form.label.email": "Digite seu email",
		"form.emailInvalido": "E-mail não encontrado para essa aplicação"
	},
	
	"resetPassword": { 
	"form.header": "Reset sua senha",		
	"label.password": "Senha",
	"label.repeat_password": "Digite a senha novamente",
	}
	
};

