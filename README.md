# 📦 WuoStore.com
**Sistema de estoque de um e-commerce**

Projeto desenvolvido com arquitetura MVC e interface gráfica (GUI) utilizando **Java e Swing**.

---

## 👥 Integrantes

| Nome | R.A. |
| :--- | :--- |
| Gabriel Galvão | 081240027 |
| Felipe Silva | 081240020 |
| Felipe Dessico | 081240002 |
| Oliver Carraro | 081240046 |

---

## 🎯 Contexto e Proposta de Uso

O WuoStore.com é um sistema desktop em Java (Swing) voltado para a gestão do estoque de produtos de um e-commerce. 

O sistema é utilizado pelo operador responsável pelo estoque, que precisa manter o catálogo sempre atualizado: cadastrar produtos novos, corrigir dados, remover itens e consultar o que existe. 

A aplicação resolve a falta de uma visão centralizada. Em uma única tela, o operador vê o preço, a quantidade e a avaliação de cada produto, além do valor acumulado em estoque. Isso auxilia diretamente no acompanhamento financeiro (valores) e qualitativo (avaliação) dos itens vendidos.

---

## 🏷️ Entidade Principal: Produto

| Atributo | Descrição |
| :--- | :--- |
| **Identificador (id)** | Código único do produto (UUID), gerado automaticamente. |
| **Nome** | Nome do produto. |
| **Preço unitário** | Valor de uma unidade. |
| **Quantidade** | Unidades em estoque. |
| **Avaliação** | Classificação do produto: *Bom* ou *Mau*. |

> 💡 **Nota:** O *Valor acumulado* mostrado na tabela é calculado automaticamente a partir do preço unitário e da quantidade.

---

## 💻 Telas e Operações

### Estrutura das Telas
* **Tela Principal:** Uma tabela com as colunas *Nome*, *Preço*, *Quantidade*, *Avaliação* e *Valor acumulado*. No alto, ficam a barra de pesquisa e os botões *Pesquisar*, *Deletar*, *Inserir* e *Editar*.
* **Tela Cadastrar Produto:** Janela contendo os campos *Nome*, *Preço unitário* e *Quantidade*, a escolha da *Avaliação* (Bom ou Mau) e os botões *Confirmar* e *Cancelar*.

### Operações Suportadas

| Operação | Como é feita na tela |
| :--- | :--- |
| **Listar** | A tabela da tela principal exibe os produtos cadastrados. |
| **Buscar** | Digita-se na barra de pesquisa e clica-se em *Pesquisar*; o resultado aparece na tabela. |
| **Incluir** | O botão *Inserir* abre a janela Cadastrar Produto; o cadastro termina em *Confirmar*, ou é abandonado em *Cancelar*. |
| **Alterar** | O botão *Editar* altera os dados de um produto já cadastrado. |
| **Excluir** | O botão *Deletar* remove o produto selecionado na tabela. |

---

## ⚠️ Regras de Validação

- [x] Nome, preço, quantidade e avaliação são **obrigatórios**.
- [x] Preço e quantidade só aceitam **valores numéricos**.
- [x] A avaliação é escolhida entre duas opções: **Bom** ou **Mau**.
- [x] O identificador (ID) é gerado automaticamente, garantindo que dois produtos **nunca** tenham o mesmo ID.
- [x] Os dois produtos padrão, *Wuo Queijo Futurista™* e *WuoDrive™*, **não podem ser excluídos**.

---

## ⚙️ Arquitetura

A aplicação segue o padrão **MVC (Model-View-Controller)**, dividida em três pacotes principais:

1. **Model:** Representa as entidades e a regra de negócios. Responsável pelo acesso ao banco de dados MariaDB do XAMPP via JDBC.
2. **View:** Telas desenvolvidas em Swing. Apenas coletam os dados de entrada do usuário e exibem os resultados e mensagens (não acessam o Model diretamente).
3. **Controller:** Intermediário que recebe as requisições da View, valida os dados e realiza as chamadas apropriadas ao Model.