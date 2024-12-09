# Requisitos do Sistema - Forever Fashion  

## Requisitos Funcionais  

### Gerenciamento de Usuários  
1. O sistema deve permitir o cadastro, edição e exclusão de usuários.  
2. Os administradores devem ter acesso total ao sistema.  
3. Funcionários devem ter restrições de acesso ao sistema.

### Gerenciamento de Produtos  
4. O sistema deve permitir que administradores cadastrem, editem e excluam produtos.  
5. Funcionários podem registrar informações sobre os produtos no estoque.  

### Registro de Vendas  
6. O sistema deve permitir que funcionários registrem vendas, incluindo informações como:  
   - Produto(s) vendido(s).  
   - Quantidade.  
   - Valor total da venda.  
   - Data e hora da venda.  

### Controle de Acesso  
9. O sistema deve utilizar autenticação para diferenciar administradores e funcionários.  
10. Apenas usuários autenticados podem acessar o sistema.  

## Requisitos Não Funcionais  

### Desempenho  
1. Operações de registro de vendas e consulta de estoque devem ser concluídas em menos de 2 segundos.  

### Segurança  
3. O sistema deve criptografar as senhas dos usuários.  
4. O sistema deve possuir mecanismos para prevenir acesso não autorizado.  

### Usabilidade  
5. A interface deve ser intuitiva e fácil de usar, com informações organizadas por função (vendas, estoque, etc.).  
6. O sistema deve ser acessível por navegadores modernos.  

### Escalabilidade  
7. O sistema deve permitir fácil adaptação para novas funcionalidades.  

### Manutenibilidade  
8. O código deve seguir boas práticas de programação.  

### Compatibilidade  
9. O sistema deve ser compatível com:  
   - Java 11 ou superior.  
   - MySQL 8.0 ou superior.  
   - Navegadores como Chrome, Firefox e Edge.  
