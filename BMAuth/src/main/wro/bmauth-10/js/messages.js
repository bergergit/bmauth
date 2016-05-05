var bmauth_translations = {};

bmauth_translations.en = {

	// Common text
	"btn.ok": "OK",
	"btn.new": "Novo",
	"btn.save": "Salvar",
	"btn.cancel": "Cancelar",
	"btn.delete": "Remover",
	"btn.send": "Enviar",
	"btn.yes": "Sim",
	"btn.no": "Não",
	"btn.add": "Adicionar",
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
	
	"navigation": {
		"exit": "Sair"
	},

	// BMAuth directive
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
		"form.msg.forgotMyPasswordCreated": "Procedimento enviado para seu e-mail",
		"form.msg.passwordReset": "Senha redefinida. Favor efetuar o login."
	},
	
	// BMAuth directive (Signup)
	"signup": {
		"header": "Cadastro",
		"header.success": "Usuário criado",
		"header.conflict": "Este email já está cadastrado no sistema.",
		"label.name": "Nome",
		"label.document_type": "CPF ou CNPJ",
		"label.email": "E-mail",
		"label.password": "Senha",
		"label.repeat_password": "Digite a senha novamente",
		"btn.register": "Cadastrar"
	},
	
	"forgotPassword": { 
		"form.header": "Redefina sua senha",
		"form.label.email": "Digite seu email",
		"form.emailInvalido": "E-mail não encontrado para essa aplicação"
	},

	"resetPassword": { 
		"form.header": "Redefina sua senha",		
		"label.password": "Senha",
		"label.repeat_password": "Digite a senha novamente",
		"message.error.400": "Solicitação de reset de senha expirada"
	},
	

	"signingContract": { 
		"form.header": "Assinatura de Contrato",
		"form.label.agreement": "Estou de acordo com os termos do contrato acima",
		"requiredMessage": "A assinatura do contrato é obrigatória para usar esse sistema."
	}
};

