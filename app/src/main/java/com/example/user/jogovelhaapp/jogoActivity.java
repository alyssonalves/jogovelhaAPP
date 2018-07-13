package com.example.user.jogovelhaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class jogoActivity extends AppCompatActivity {

    //guarda a instancia da nossa view
    private View view; //criar os gets e sets


    //difinicao de constantes para recuperar o butao atraves do metodo getQuadrado
    private final String QUADRADO = "quadrado";

    private final String BOLA = "O";
    private final String XIS = "X";

    private String lastPlay = "X"; //criar gest e sets

    //matriz que define as codicoes para o jogo acabar
    int[][] estadoFinal = new int[][]{

            //verificacao das linhas
            {1,2,3},
            {4,5,6},
            {7,8,9},

            //verificacao das colunas
            {1,4,7},
            {2,5,8},
            {3,6,9},

            //verificacao das diagonais
            {1,5,9},
            {3,5,7}
    };

    private int totalJogadas = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recupera a nosso xml(tela) e armazena na instancia view
        setView(getLayoutInflater().inflate(R.layout.activity_jogo, null));

        //passa a instancia da VIEW para a Activity
        setContentView(getView()); //estava chamando a activity
    }

    //recupera qual quadrado está sendo clicado pela TAG
    public Button getQuadrado(int tagNum){
        return (Button) getView().findViewWithTag(QUADRADO + tagNum);
    }

    public void clickQuadrado(View v){

        //deixar jogar(clicar) apenas se o jogo não tive acabado
        if(!isFim()){

            totalJogadas++;

            if(((Button)v).getText().equals("")){  //verificar se o texto é vazio (ninguem jogou)

                if(getLastPlay().equals(XIS)){ //verifica se a ultima jogada foi X
                    ((Button)v).setText(BOLA);
                    setLastPlay(BOLA);
                }else {
                    ((Button)v).setText(XIS);
                    setLastPlay(XIS);
                }
            }else {
                Toast.makeText(getView().getContext(), v.getTag().toString(), Toast.LENGTH_LONG).show();
            }

            isFim();
        }
        //imprime o butão que está sendo clicado
        //Toast.makeText(getView().getContext(), "Opa! Escolha outro quadrado", Toast.LENGTH_LONG).show();
    }

    public void newGame(View v){

        //caso coloque o ID na tela, esse seria a forma de limpar todos os butoes
        //((Button) findViewById(R.id.quadrado1)).setText("");

        setEnableQuadrado(true); //ativa todos os butoes

        //setar a cor preta para iniciar o jogo
        setColorBlack();

        //limpar os quadrados
        limpaCampos();

        //voltar o numeros de jogadas para Zero
        totalJogadas = 0;

        //retorna a instancia do radiobutton e armazena na variavel
        RadioButton rX = (RadioButton) getView().findViewById(R.id.rdX);
        RadioButton rO = (RadioButton) getView().findViewById(R.id.rdO);

        if(rX.isChecked()){
            setLastPlay(BOLA); // se X tiver marcado, o ultimo a ter jogado foi BOLA, portando é a vez do XIS
        }else {
            if(rO.isChecked()){
                setLastPlay(XIS);
            }
        }
    }

    public void limpaCampos(){
        //perrocer todos os butoes e setar como VAZIO se for diferente de null
        for(int i = 1;  i<=9; ++i){
            if(getQuadrado(i) != null){
                getQuadrado(i).setText("");
            }
        }
    }

    public void setEnableQuadrado(boolean b){
        //perrocer todos os butoes, verifica se é diferente de NULL e muda o estado para a valor da variavel b
        for(int i = 1;  i<=9; ++i){
            if(getQuadrado(i) != null){
                getQuadrado(i).setEnabled(b);
            }
        }
    }

    //setar a cor VERMELHA para indicar quem ganhou e que o jogo acabou
    public void setColorQuadrados(int btn, int colorX){

        //recebe o num do botao pela var btn
        //passa a instancia da cor pela variavel colorX
        //recuperao o botão se seta o textColor com a cor passada pela variável colorX
        getQuadrado(btn).setTextColor(getResources().getColor(colorX));

    }

    //setar a cor PRETA nos botoes quando iniciar o jogo
    public void setColorBlack(){
        for(int i = 1; i<=9; ++i){
            if(getQuadrado(i) != null){
                setColorQuadrados(i, R.color.black);
            }
        }
    }

    //desenvolver primeiro com o retorno VOID. O jogo continua mesmo depois de terminar
    // o retorno bolleano é para poder não deixar jogar se o jogo acabou
    public boolean /*void*/ isFim(){

        //percorre todos as condições da matriz
        for(int x= 0; x <=7; ++x){
            String s1 = getQuadrado(estadoFinal[x][0]).getText().toString();
            String s2 = getQuadrado(estadoFinal[x][1]).getText().toString();
            String s3 = getQuadrado(estadoFinal[x][2]).getText().toString();

            //verifica se s1, s2, s3 é vazio, isso é precisso pois o jogo acabaria na primeira rodada
            //porque os botoes irão conter o mesmo texto
            if(!s1.equals("") && !s2.equals("") && !s3.equals("")){

                //verifica se as jogas são iguais
                if(s1.equals(s2) && s2.equals(s3)){

                    //caso os tres botoes sejam iguais, pintar de vermelho
                    setColorQuadrados(estadoFinal[x][0], R.color.red);
                    setColorQuadrados(estadoFinal[x][1], R.color.red);
                    setColorQuadrados(estadoFinal[x][2], R.color.red);

                    //indica que o jogo acabou
                    Toast.makeText(getView().getContext(), "Fim de Jogo", Toast.LENGTH_LONG).show();

                    return true;
                }
            }
        }

        if(totalJogadas == 9){
            Toast.makeText(getView().getContext(), "Fim de Jogo. Empate!", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(String lastPlay) {
        this.lastPlay = lastPlay;
    }
}
