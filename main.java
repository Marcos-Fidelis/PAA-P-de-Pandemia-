import java.util.Random;

calculaDiversao(int[] solucao, int tempoMax) {
    if(solucao.getTempo() > tempoMax) {
        solucao.setDiversao(0);
    }
    else {
        solucao.setDiversao(solucao.getDiversao());
    }
}

main {
    Random rand = new Random()
    int numeroAtracoes = rand.nextInt(10);
    int janelaTempo = rand.nextInt(25);
    int[numeroAtaacoes] planejamento;

    for (int a = 0; a < numeroAtracoes; a++ ) {
        planejamento[a] = rand.nextInt(5);
    }

    for (int a = 0; a < numeroAtracoes; a++) {

    }



    int diversaoPlanejamento = calculaDiversao(planejamento, janelaTempo);

    return 0;
}