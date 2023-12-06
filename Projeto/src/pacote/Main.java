package pacote;

import java.text.MessageFormat;
import java.util.*;

public class Main {
    private static void mostrarMes(int numeroMes){
        if (numeroMes < 1 || numeroMes > 12) throw new IllegalArgumentException("Mês incorreto!");

        Calendar date = new GregorianCalendar(2023, 0, numeroMes);
        int semanas = date.get(Calendar.DAY_OF_WEEK) - 2;

        String[] nomesDosMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julo", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        String[] mes = new String[8];
        String nomeMes = nomesDosMeses[numeroMes-1];

        int comprimento = 11 + nomeMes.length() / 2;
        String formatacao = MessageFormat.format("%{0}s%{1}s", comprimento, 21 - comprimento);

        mes[0] = String.format(formatacao, nomeMes, "");
        mes[1] = " Se Te Qu Qu Se Sa Do";
        int ultimoDia = getUltimoDia(numeroMes);

        for (int d = 1; d < 43; d++) {
            boolean isDay = d > semanas && d <= semanas + ultimoDia;
            String entry = isDay ? String.format(" %2s", d - semanas) : "   ";
            if (d % 7 == 1)
                mes[2 + (d - 1) / 7] = entry;
            else
                mes[2 + (d - 1) / 7] += entry;
        }

        for (int i = 0; i < 8; i++) System.out.print(mes[i]+"\n");
    }

    private static int getUltimoDia(int numeroMes){
        return numeroMes==2?28:numeroMes==4||numeroMes==6||numeroMes==9||numeroMes==11?30:31;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Map<Integer, List<String>>> ano = new HashMap<>();
        String[] mesesDoAno = {"Janeiro",
                "Fevereiro", "Março", "Abril", "Maio",
                "Junho", "Julho", "Agosto", "Setembro",
                "Outubro", "Novembro", "Dezembro"};

        while (true) {
            try {
                System.out.print("=== Agenda Pessoal ===" +
                        "\nO que deseja?" +
                        "\n1 - Consultar Agenda" +
                        "\n2 - Marcar Evento" +
                        "\n3 - Desmarcar Evento" +
                        "\n4 - Sair" +
                        "\nOpção: ");
                int opcao = Integer.parseInt(sc.next());
                if (opcao < 1 || opcao > 4) throw new Exception("\nOpção Inválida!");
                System.out.println();
                if (opcao == 1) {
                    while (true) {
                        System.out.print("Qual mês deseja consultar?" +
                                "\n1 - Janeiro   7 - Julho" +
                                "\n2 - Fevereiro 8 - Agosto" +
                                "\n3 - Março     9 - Setembro" +
                                "\n4 - Abril     10 - Outubro" +
                                "\n5 - Maio      11 - Novembro" +
                                "\n6 - Junho     12 - Setembro" +
                                "\nOpção: ");
                        int mes = Integer.parseInt(sc.next());
                        if (mes < 1 || mes > 12) throw new Exception("\nData Inválida!");
                        mostrarMes(mes);
                        System.out.println("Qual dia deseja consultar? ");
                        int dia = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > getUltimoDia(mes)) throw new Exception("\nData Inválida!");

                        Optional<List<String>> optional = Optional.empty();
                        if (ano.containsKey(mesesDoAno[mes - 1]))
                            if (ano.get(mesesDoAno[mes - 1]).containsKey(dia))
                                optional = Optional.of(ano.get(mesesDoAno[mes - 1]).get(dia));

                        if (optional.isPresent()) System.out.println("\nMês: " + mesesDoAno[mes - 1] + ", Dia: " + dia);
                        optional.ifPresentOrElse(
                                lista -> lista.forEach(evento -> System.out.println("Evento: " + evento)),
                                () -> System.out.println("\nNão há eventos!"));

                        System.out.print("\nDeseja consultar outra data?" +
                                "\n1 - Sim" +
                                "\n2 - Não" +
                                "\nOpção: ");
                        opcao = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > 2) throw new Exception("\nOpção Inválida!");
                        System.out.println();
                        if (opcao == 2) break;
                    }
                } else if (opcao == 2) {
                    while (true) {
                        System.out.print("Qual mês deseja marcar?" +
                                "\n1 - Janeiro   7 - Julho" +
                                "\n2 - Fevereiro 8 - Agosto" +
                                "\n3 - Março     9 - Setembro" +
                                "\n4 - Abril     10 - Outubro" +
                                "\n5 - Maio      11 - Novembro" +
                                "\n6 - Junho     12 - Setembro" +
                                "\nOpção: ");
                        int mes = Integer.parseInt(sc.next());
                        if (mes < 1 || mes > 12) throw new Exception("\nData Inválida!");
                        mostrarMes(mes);
                        System.out.println("Qual dia deseja marcar? ");
                        int dia = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > getUltimoDia(mes)) throw new Exception("\nData Inválida!");

                        Map<Integer, List<String>> diasParaMarcar = new HashMap<>();
                        List<String> eventosParaMarcar = new ArrayList<>();
                        sc.nextLine();
                        while (true) {
                            System.out.println("\n(Digite 0 [Zero] para encerrar)\nQual o seu evento? ");
                            String resposta = sc.nextLine();
                            if (resposta.equals("0")) break;
                            eventosParaMarcar.add(resposta);
                        }
                        diasParaMarcar.put(dia, eventosParaMarcar);

                        if (ano.containsKey(mesesDoAno[mes - 1])) {
                            Map<Integer, List<String>> diasMarcados = ano.get(mesesDoAno[mes - 1]);
                            if (diasMarcados.containsKey(dia)) {
                                List<String> eventosMarcados = diasMarcados.get(dia);
                                eventosMarcados.addAll(diasParaMarcar.get(dia));
                            } else
                                diasMarcados.putAll(diasParaMarcar);
                        } else
                            ano.put(mesesDoAno[mes - 1], diasParaMarcar);

                        System.out.print("\nDeseja marcar outra data?" +
                                "\n1 - Sim" +
                                "\n2 - Não" +
                                "\nOpção: ");
                        opcao = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > 2) throw new Exception("\nOpção Inválida!");
                        System.out.println();
                        if (opcao == 2) break;
                    }
                } else if (opcao == 3) {
                    while (true) {
                        System.out.println("Qual mês deseja desmarcar?" +
                                "\n1 - Janeiro   7 - Julho" +
                                "\n2 - Fevereiro 8 - Agosto" +
                                "\n3 - Março     9 - Setembro" +
                                "\n4 - Abril     10 - Outubro" +
                                "\n5 - Maio      11 - Novembro" +
                                "\n6 - Junho     12 - Setembro" +
                                "\nOpção: ");
                        int mes = Integer.parseInt(sc.next());
                        if (mes < 1 || mes > 12) throw new Exception("\nData Inválida!");
                        mostrarMes(mes);
                        System.out.println("Qual dia deseja desmarcar? ");
                        int dia = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > getUltimoDia(mes)) throw new Exception("\nData Inválida!");

                        Optional<List<String>> optional = Optional.empty();
                        if (ano.containsKey(mesesDoAno[mes - 1]))
                            if (ano.get(mesesDoAno[mes - 1]).containsKey(dia))
                                optional = Optional.ofNullable(ano.get(mesesDoAno[mes - 1]).get(dia));

                        if (optional.isPresent()) {
                            System.out.println("\nMês: " + mesesDoAno[mes - 1] + ", Dia: " + dia);
                            optional.get().forEach(evento -> System.out.println("Evento: " + evento));

                            sc.nextLine();
                            while (true) {
                                if (optional.get().size() == 0) {
                                    ano.get(mesesDoAno[mes - 1]).remove(dia);
                                    break;
                                }
                                System.out.println("\n(Digite 0 [Zero] para encerrar)\nQual evento deseja desmarcar?");
                                String resposta = sc.nextLine();
                                if (resposta.equals("0")) break;
                                if (optional.get().contains(resposta)) {
                                    optional.get().remove(resposta);
                                    System.out.println();
                                    optional.get().forEach(evento -> System.out.println("Evento: " + evento));
                                } else
                                    System.out.println("\nEvento incorreto, digite novamente!");
                            }
                        } else
                            System.out.println("\nNão há eventos!");

                        System.out.print("Deseja desmarcar outra data?" +
                                "\n1 - Sim" +
                                "\n2 - Não" +
                                "\nOpção:");
                        opcao = Integer.parseInt(sc.next());
                        if (opcao < 1 || opcao > 2) throw new Exception("\nOpção Inválida!");
                        System.out.println();
                        if (opcao == 2) break;
                    }
                } else
                    break;
            } catch (Exception e) {
                System.out.println("\nOpção Inválida (Reinicinado). ");
            }
        }
    }
}