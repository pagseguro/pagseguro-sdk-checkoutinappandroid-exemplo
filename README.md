![ps5.png](https://bitbucket.org/repo/4naLKz/images/1051242651-ps5.png)

# Guia de Integração #
* **
**Biblioteca Android PagSeguro UOL - Checkout Transparente Manual de Uso**

<hr>
   **Histórico de Versões**                                                                                      
   - 0.0.1 : **Versão inicial**  - 16/02/2017
 <hr>

**Copyright**

Todos os direitos reservados. O UOL é uma marca comercial do UNIVERSO ONLINE S / A. O logotipo do UOL é uma marca comercial do UNIVERSO ONLINE S / A. Outras marcas, nomes, logotipos e marcas são de propriedade de seus respectivos proprietários.
As informações contidas neste documento pertencem ao UNIVERSO ONLINE S/A. Todos os direitos reservados. UNIVERSO ONLINE S/A. - Av. Faria Lima, 1384, 6º andar, São Paulo / SP, CEP 01452-002, Brasil.
O serviço PagSeguro não é, nem pretende ser comparável a serviços financeiros oferecidos por instituições financeiras ou administradoras de cartões de crédito, consistindo apenas de uma forma de facilitar e monitorar a execução das transações de comércio electrónico através da gestão de pagamentos. Qualquer transação efetuada através do PagSeguro está sujeita e deve estar em conformidade com as leis da República Federativa do Brasil.
Aconselhamos que você leia os termos e condições cuidadosamente.


**Aviso Legal**

O UOL não oferece garantias de qualquer tipo (expressas, implícitas ou estatutárias) com relação às informações nele contidas. O UOL não assume nenhuma responsabilidade por perdas e danos (diretos ou indiretos), causados por erros ou omissões, ou resultantes da utilização deste documento ou a informação contida neste documento ou resultantes da aplicação ou uso do produto ou serviço aqui descrito. O UOL reserva o direito de fazer qualquer tipo de alterações a quaisquer informações aqui contidas sem aviso prévio.

* **

**Visão Geral**

A biblioteca Checkout Transparente tem como foco auxiliar desenvolvedores que desejam prover em seus aplicativos toda a praticidade e segurança fornecida pelo PagSeguro no segmento de pagamentos móveis através de smartphones e tablets. Para ajudar a entender como a biblioteca pode ser utilizada, apresentamos o seguinte cenário:

• Cenário Exemplo: Solução de pagamentos com Checkout Transparente. A empresa X desenvolve um aplicativo para seus clientes permitindo-os efetuar pagamento de serviços prestados ou itens (produtos) vendidos. Neste cenário o aplicativo da empresa X faz uso da biblioteca PagSeguro "Checkout Transparente" autorizando a Library com a sua conta PagSeguro (E-mail vendedor e Token referente da conta). Os clientes da empresa X realizam o pagamento no app sem sair do App, todos os dados do cartão são passados no próprio aplicativo da empresa. A empresa X receberá os pagamentos em sua conta PagSeguro configurada como vendedor na Lib Checkout Transparente.


* **
**Conceitos Básicos**

Antes de fazer uso da biblioteca é importante que o desenvolvedor realize alguns procedimentos básicos, além de assimilar alguns conceitos importantes para o correto funcionamento de sua aplicação. É necessário ter em mãos o token da conta PagSeguro que será configurado como vendedor (Seller), tal token pode ser obtido no iBanking do PagSeguro. (Vide tópico abaixo).

* **

**Para utilizar o Checkout Transparente**

O Checkout Transparente está operando em fase de piloto. Para fazer parte deste piloto você precisa seguir alguns passos:

- Enviar um e-mail para checkoutinapp@pagseguro.com.br informando um telefone de contato e o e-mail da sua conta PagSeguro.  
Se você for selecionado para o piloto, nossa equipe entrará em contato com você para obter mais informações e liberar a funcionalidade para a sua conta;
- Implementar o Checkout Transparente em sua aplicação;
- Encaminhar os feedbacks para a nossa equipe. Nesta fase do projeto a sua opinião será extremamente importante.

A equipe do PagSeguro dará todo o suporte para sua integração do Checkout in App com a sua aplicação.

* **

**Obtendo Token da conta PagSeguro**

Para realizar transações utilizando a biblioteca é necessária uma conta PagSeguro. Caso não tenha uma Acesse: www.pagseguro.com.br.
Com a conta PagSeguro criada é necessário ter o Token da conta que será utilizada na configuração como vendedor na Library Checkout Transparente.

OBTENDO TOKEN DA CONTA PAGSEGURO PARA INTEGRAÇÃO COM API's

Na pagina do ibanking do PagSeguro em sua conta:

1- Click na guia **"Minha Conta"**;

2- No Menu lateral clique em **"Preferências"**;

3- Pressione o botão **"Gerar Token"**;

4- Armazene esse **TOKEN** em algum lugar pois iremos utilizá-lo a seguir nesse guia de integração.

* **
**Requisitos Mínimos**

Antes de iniciar o guia  de integração com a Library Checkout Transparente é válido ressaltar alguns requisitos mínimos para funcionamento da Biblioteca.

**Versão Android:** Android 4.0.3;

Conexão com internet;

Permissões no AndroidManifest.xml (Vide tópico abaixo);

Importar corretamente as dependências no .gradle;


* **
## **Permissões AndroidManifest.xml** ##

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

* **
## **Instalação** ##
Via gradle:

```json
repositories {

       mavenCentral()

        maven {
            url "https://jitpack.io"
        }

        maven {
            url "https://github.com/pagseguro/android-checkout-transparent-in-app/raw/master/repositorio"
        }

        jcenter()
}


dependencies {
    ...
    compile 'br.com.uol.pslibs:android-checkout-transparent-in-app:0.0.1'
    ...
}

```

* **
**Metodologia**
Vejamos agora como integrar a biblioteca Checkout Transparente do PagSeguro em seu aplicativo Android.

Para utilizar a library **Checkout Transparente** do PagSeguro UOL para Android são necessários duas etapas:

**1 – Implementação base:** Configurar controle de permissão;

**2 – Autorização:** **PSCheckout.init()** - Configurações das credenciais da conta PagSeguro como vendedor na Lib;

**3 – Pagamento:** **PSCheckout.pay()** - Para utilizar o método de pagamento deve seguir uma estrutura de parametrização;

* **
**1 - Implementação base**

Antes de utilizar a biblioteca é necessário a configuração da activity hospedeira para permitir o correto funcionamento das permissões utilizadas pela lib. Para isso na Activity hospedeira dever ser sobrescrito o método ```onRequestPermissionsResult```.

Exemplo:

```java
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PSCheckout.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
```
**Obs: A lib Checkout Transparente irá solicitar a permissão PERMISSION_READ_PHONE_STATE. Essa permissão é utilizada como uma das técnicas de segurança para realização das transações. Caso essa permissão não seja concedida a transação não poderá ser feita.**

* **
**2 - Autorização**

Antes de utilizar os demais métodos da biblioteca é de extrema importância passar as configuração para inicialização da Lib Checkout Transparente.

Configuração de inicialização da Lib:

• (String)Seller Email (E-mail da conta que será utilizado como vendedor);

• (String)Seller Token (Token da conta que será utilizado como vendedor, foi explicado anteriormente nessa documentação como obter esse token);

• (Context) Context context;

Exemplo de inicialização da Lib:

```java
    SellerVO sellerVO = new SellerVO(SELLER_EMAIL, SELLER_TOKEN);
    PSCheckout.init(getActivity(), sellerVO);
```
* **
**2 - Pagamento**

Para realização do pagamento devemos utilizar o método **PSCheckout.pay()**, deve ser passado três parâmetros nesse método:

**PSCheckoutRequest ->** Objeto que vai conter informações necessárias para o processamento do pagamento;

**PSCheckout.PSCheckoutListener ->** Esse listener será o ponto focal para validar o status das operações de pagamentos da Library Checkout Transparente.

**AppCompatActivity ->** É a instancia da Activity referenciada no passo 1 (Implementação base). Deve ser passado para permitir a lib notificar o status das permissões solicitadas.

Abaixo vamos demonstrar como funciona o objeto PSCheckoutRequest:

```java
        PSCheckoutRequest psCheckoutRequest = new PSCheckoutRequest();

        //NUMERO DO CARTAO
        psCheckoutRequest.setCreditCard(number);
        //CVV DO CARTAO
        psCheckoutRequest.setCvv(cvv);
        //MÊS DE EXPIRACAO (Ex: 03)
        psCheckoutRequest.setExpMonth(expiry.substring(0,2));
        //ANO DE EXPIRACAO, ULTIMOS 2 DIGITOS (Ex: 17)
        psCheckoutRequest.setExpYear(expiry.substring(3,5));
        //VALOR DA TRANSACAO
        psCheckoutRequest.setAmountPayment(1.00);
        //DESCRICAO DO PRODUTO/SERVICO
        psCheckoutRequest.setDescriptionPayment("test");

        PSCheckout.pay(psCheckoutRequest, psCheckoutListener, (AppCompatActivity) getActivity());
```

Agora uma demonstração de utilização do PSCheckout.PSCheckoutListener:

```java
     private PSCheckout.PSCheckoutListener psCheckoutListener = new PSCheckout.PSCheckoutListener() {
        @Override
        public void onSuccess(PaymentResponseVO responseVO) {
            Toast.makeText(getActivity(), "Success: "+responseVO.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(PaymentResponseVO responseVO) {
            Toast.makeText(getActivity(), "Fail: "+responseVO.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProcessing() {
            Toast.makeText(getActivity(), "Processing...", Toast.LENGTH_LONG).show();
        }
    };

```

Exemplo chamada do método Pay():

```java
 PSCheckout.pay(psCheckoutRequest, psCheckoutListener);
```
**Obs: Os trechos utilizados nesta documentação foram retirados da aplicação de exemplo disponível nesse repositório.**

* **
**Código de Erros**

Abaixo seguem os códigos de erro que podem ser retornados pela biblioteca:

**1001** - INVALID_CARD_NUMBER (Número do cartão inválido);

**1002** - INVALID_CARD_YEAR (Ano de validade do cartão inválido);

**1003** - INVALID_CARD_MONTH (Mês de validade do cartão inválido);

**1004** - INVALID_AMOUNT (Valor do pagamento inválido);

**1005** - INVALID_CVV (Número do CVV inválido);

**1006** - FAILURE_CARD_BRAND (Falha ao consultar a bandeira do cartão (número do cartão inválido));

**1007** - INSUFFICIENT_PERMISSION (Permissão insuficente);

**9000** - NETWORK_ERROR (Falha de conexão);

**9005** - FAILURE_PAYMENT (Falha ao realizar pagamento);

**9006** - CANCELED_PAYMENT (Cartão não aprovado);

**9007** - TIMEOUT_ERRROR (Timeout na verificação da transação);

**9999** - UNKNOWN_ERROR (Erro desconhecido);

Obs: Para acesso a um ENUM contendo esses erros mapeados utilize a classe: **ErrorCode.java**
* **
**UOL - O melhor conteúdo**

© 1996 - 2017 O melhor conteúdo. Todos os direitos reservados.
UNIVERSO ONLINE S/A - CNPJ/MF 01.109.184/0001-95 - Av. Brigadeiro Faria Lima, 1.384, São Paulo - SP - CEP 01452-002 
<hr>

