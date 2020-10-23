import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AlgoritmoGenetico {
    static ArrayList<Integer[]> mutação(ArrayList<Integer[]> filhos) {
        Random rand = new Random();
        boolean mutado = false;
        for(int i = 0; i < filhos.size(); i++) {
            int rolagem = rand.nextInt(101);
            if(rolagem >= 40) {
                while (!mutado) {
                    int mutagene1 = rand.nextInt(filhos.get(i).length + 1);
                    int mutagene2 = rand.nextInt(filhos.get(i).length + 1);
                    if (mutagene1 != mutagene2 && filhos.get(i)[mutagene1] < 0) {
                        filhos.get(i)[mutagene1] -= 1;
                        filhos.get(i)[mutagene2] += 1;
                        mutado = true;
                    }
                }
            }
        }
        return filhos;
    }
    static Integer[] mutação(Integer[] individuo) {
        boolean bug = true;
        for(int i = 0; i < individuo.length; i++){
            if(individuo[i] != 0){
                bug = false;
            }
        }

        Random rand = new Random();

        if(bug){
            int index = rand.nextInt(individuo.length);
            individuo[index]++;
        }
        else{
            boolean mutado = false;
            int mutagene1, mutagene2;
            while (!mutado) {
                mutagene1 = rand.nextInt(individuo.length);
                mutagene2 = rand.nextInt(individuo.length);
                if (mutagene1 != mutagene2 && individuo[mutagene1] != 0) {
                    individuo[mutagene1] -= 1;
                    individuo[mutagene2] += 1;
                    mutado = true;
                }
            }
        }
        return individuo;
    }

    static Integer[] crossover(Integer[] p1, Integer[] p2) {
        Random aleatorizador = new Random();
        int numeroAleatorio = aleatorizador.nextInt(p1.length);

        Integer[] novo_individuo = p1.clone();

        for(int i = numeroAleatorio; i < p1.length; i++) {
            novo_individuo[i] = p2[i];
        }

        return novo_individuo;
    }

    //// CORRETA
    static int verificaAptidao (Integer[] individuo, int[][] elementos, int tempoMax) {
        int valor = 0;
        int peso = 0;
        for (int a = 0; a < individuo.length; a++) {
            peso += individuo[a]*elementos[a][0];
        }
        if (peso > tempoMax) {
            valor = 0;
        }
        else {
            for (int a = 0; a < individuo.length; a++) {
                valor += individuo[a]*elementos[a][1];
            }
        }
        return valor;
    }

    public static void main(String[] args) {
        //// Variaveis usadas para alterar as configurações do algoritmo genetico
        int TAM_POPULACAO = 100;

        float aux = (float)(TAM_POPULACAO/2);
        int N_PAIS = Math.round(aux);

        int N_MAX_INTERACOES = 10000;

        float mutation_chance = 0.25f;

        // usada para finalizar o programa
        boolean terminar_programa = false;

        // a classe Scanner eh utilizada para ler informações passadas pelo usuario
        Scanner scanner = new Scanner(System.in);

        int n_interacoes = 0;
        do {
            int n = scanner.nextInt(); // consegue o valor de N
            int t = scanner.nextInt(); // consegue o valor de T

            if (n >= 0 && n <= 100 && t >= 0 && t <=600)
            {
                if(n == 0)
                {
                    terminar_programa = true;
                }
                else
                {
                    //// Leitura dos dados

                    // [n][0] representa a duração de cada brinquedo
                    // [n][1] representa os pontos de cada brinquedo
                    int[][] brinquedos = new int[n][2];

                    boolean entrada_invalida = false;

                    // preenche o vetor de brinquedos
                    for(int i = 0; i < n; i++) {
                        int d = scanner.nextInt();
                        int p = scanner.nextInt();

                        if(d < 0 || d > 600 || p < 0 || p > 100)
                        {
                            entrada_invalida = true;
                            break;
                        }
                        brinquedos[i][0] = d;
                        brinquedos[i][1] = p;
                    }

                    if(entrada_invalida){
                        break;
                    }

                    //// Otimização
                    int maior_peso = 0;
                    for(int i = 0; i < n; i++){
                        if(brinquedos[i][0] > maior_peso && maior_peso <= t){
                            maior_peso = brinquedos[i][0];
                        }
                    }

                    //int MAX_VALUE = Math.round((float)t/maior_peso) + 2;
                    //// Algoritmo genetico

                    // Criar a populacao inicial
                    ArrayList<Integer[]> populacao = new ArrayList<Integer[]>();

                    for(int i = 0; i < TAM_POPULACAO; i++){
                        Integer[] ind = new Integer[n];
                        Random rand = new Random();
                        for(int j = 0; j < n; j++){
                            ind[j] = rand.nextInt(t/n); // Cada brinquedo pode aparecer de 0 a 10 vezes
                        }
                        populacao.add(ind);
                    }

                    //// Entra no ciclo
                    boolean populacao_final = false; // usada para finalizar o ciclo

                    int num_interacoes = 0;

                    do {
                        num_interacoes++;

                        // Aplica metodo de Selecao para encontrar os pais da proxima geracao
                        ArrayList<Integer[]> pais = new ArrayList<Integer[]>();

                        int somatorio = 0;
                        int contador = 0;

                        for(int i = 0; i < TAM_POPULACAO; i++){
                            if(verificaAptidao(populacao.get(i),brinquedos,t) != 0){
                                somatorio += verificaAptidao(populacao.get(i), brinquedos, t);
                            }
                            else {
                                Integer[] ind = new Integer[n];
                                Random rand = new Random();
                                for(int j = 0; j < n; j++){
                                    ind[j] = rand.nextInt(t/n); // Cada brinquedo pode aparecer de 0 a 10 vezes
                                }
                                somatorio += verificaAptidao(ind, brinquedos, t);
                                populacao.add(i, ind);
                                populacao.remove(i+1);
                            }
                        }

                        if(somatorio != 0){
                            int[] probs_index = new int[TAM_POPULACAO];

                            for(int i = 0; i < TAM_POPULACAO; i++){
                                float prob = (float)verificaAptidao(populacao.get(i), brinquedos, t) / somatorio;
                                probs_index[i] = Math.round(prob * 100);
                            }

                            while(contador < N_PAIS){
                                Random rand = new Random();
                                int acumulada = 0;
                                int chance = rand.nextInt(100) + 1;

                                for(int i = 0; i < TAM_POPULACAO; i++){
                                    acumulada += probs_index[i];

                                    if(acumulada >= chance){
                                        pais.add(populacao.get(i).clone());
                                        contador++;
                                        break;
                                    }
                                }
                            }
                        }
                        else{
                            Random rand = new Random();
                            while(contador < N_PAIS){
                                int index = rand.nextInt(populacao.size());
                                pais.add(populacao.get(index).clone());
                                contador++;
                            }
                        }

                        populacao.clear();
                        populacao.addAll(pais);
                        // Utiliza os pais como parametros para os metodos de Crusamento e Mutacao
                        // funcao de crusamento
                        while(contador < TAM_POPULACAO){
                            for(int i = 0; i < pais.size(); i++){
                                if(contador >= TAM_POPULACAO){
                                    break;
                                }

                                if(i+1 == pais.size()) {
                                    populacao.add(crossover(pais.get(i), pais.get(0)));
                                }
                                else {
                                    populacao.add(crossover(pais.get(i), pais.get(i+1)));
                                }
                                contador++;
                            }
                        }

                        // funcao de mutacao
                        for(int i = 0; i < TAM_POPULACAO; i++){
                            Random rand = new Random();
                            if(rand.nextFloat() <= mutation_chance){
                                populacao.add(mutação(populacao.get(i)).clone());
                                populacao.remove(i);
                                i--;
                            }
                        }

                        // Função de termino das iteracoes
                        if(num_interacoes >= N_MAX_INTERACOES){
                            populacao_final = true;
                        }
                        // System.out.println("Num interacoes = " + num_interacoes);
                    }while(!populacao_final);

                    Integer[] melhor_ind = new Integer[n];

                    for(int i = 0; i < n; i++){
                        melhor_ind[i] = 0;
                    }

                    for(int i = 0; i < TAM_POPULACAO; i++){
                        int melhor_fit = verificaAptidao(melhor_ind, brinquedos, t);
                        int new_fit = verificaAptidao(populacao.get(i), brinquedos, t);

                        if(new_fit > melhor_fit){
                            for(int j = 0; j < n; j++)
                            {
                                melhor_ind[j] = populacao.get(i)[j];
                            }
                        }
                    }

                    n_interacoes++;
                    System.out.println("Instancia " + n_interacoes);
                    System.out.println(verificaAptidao(melhor_ind, brinquedos, t) + "\n");
                }
            }
            else // caso os valores de N ou T estejam invalidos ele entra nesse else
            {
                // Eu assumi que caso ele leia uma valor invalido para N ou T o algoritmo simplesmente para
                terminar_programa = true;
            }
        }while(!terminar_programa);

        scanner.close();
    }
}
