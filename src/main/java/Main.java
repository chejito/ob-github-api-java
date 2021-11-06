import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        final String MY_PAT = System.getenv("MI_PAT"); //Variable de entorno, cambiar por la que corresponda en el sistema.
        final String MY_USER = System.getenv("MI_GITHUB_USER"); //Variable de entorno, cambiar por la que corresponda en el sistema.
        final String BASE_URL = "https://github.com/users/";

        String githubUser;
        String token;
        String authorization;
        String authType;


        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                ############################################
                Cliente de GitHub en línea de comandos (CLI)
                ############################################
                """);

        // Introducción de datos del usuario de GitHub
        githubUser = getToken(scanner, "Introduce el usuario de GitHub que quieres consultar: ");

        System.out.println("El usuario de GitHub es: " + BASE_URL + githubUser);


        do {
            System.out.println("¿Quieres utilizar variables de sistema para autenticar? S/N:");
            authType = scanner.nextLine();

        } while (!Objects.equals(authType.toLowerCase(), "s") && !Objects.equals(authType.toLowerCase(), "n"));

        if (Objects.equals(authType.toLowerCase(), "n")) {

            token = getToken(scanner, "Introduce tu token: ");
            System.out.println(("Tu token es: " + token));

        } else {

            token = MY_PAT;

        }

        authorization = "Bearer " + token;

        System.out.println();

        try {

            ArrayList<Map> repos = ApiCall.getRepos(githubUser + "/repos", authorization);

            System.out.println("Usuario: " + githubUser + " - Número de repositorios: " + repos.size());
            System.out.println();

            for (Map repo : repos) {

                String repoName = repo.get("name").toString();

                try {

                    ArrayList<Map> commits = ApiCall.getCommits(githubUser + "/" + repoName + "/commits", authorization);

                    System.out.println("-> Repositorio: " + repoName + "\n-> Número de commits: " + commits.size());

                    for (Map commit : commits) {

                        String commitSha = commit.get("sha").toString();
                        System.out.println("---> Sha: " + commitSha);

                    }

                    System.out.println();

                } catch (Exception e) {

                    System.out.println("-> Repositorio: " + repoName + "\n-> No se han podido encontrar commits");

                }

            }

        } catch (Exception e) {

            System.out.println("No se ha podido encontrar el repositorio");

        }

        scanner.close();

    }

    private static String getToken(Scanner scanner, String message) {

        String userToken;

        do {

            System.out.println(message);
            userToken = scanner.nextLine();

        } while (userToken.equals(""));

        return userToken;

    }

}
