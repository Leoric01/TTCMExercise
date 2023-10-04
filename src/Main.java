import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("zadejte budto cisla oddelena mezerou, nebo cestu k souboru odkud cisla nacist, nebo muzete pridat soubor kam vysledek ulozit ");
        }
        processArgsFromCLI(args);
    }

    public static void processArgsFromCLI(String[] ar){
        List<Integer> numbers = new ArrayList<>();
        if (ar.length > 2){
            for (String str : ar){
                for (char c : str.toCharArray()){
                    if (!Character.isDigit(c)){
                        System.out.println("zadavejte pouze cisla nebo jen adresu zdroje, popripade muzete pridat adresu vystupu");
                        return;
                    }
                }
                numbers.add(Integer.parseInt(str));
            }List<Integer> processedNumbers = processList(numbers);
            System.out.println(processedNumbers);
        }
        else if (ar.length == 1){
            List<Integer> fileInput = processList(Objects.requireNonNull(readNumbersFromFile(ar[0])));
            System.out.println(fileInput);
        }else if (ar.length == 2){
            writeNumbersToFile(processList(Objects.requireNonNull(readNumbersFromFile(ar[0]))),ar[1]);
        }
    }

    public static void writeNumbersToFile(List<Integer> numbers, String filename){
        Path filePath = Paths.get("src\\assets\\"+filename);
        List<String> content = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int x : numbers){
            sb.append(x);
            sb.append(",");
        }
        content.add(sb.substring(0, sb.length()-1));
        try {
            Files.write(filePath,content); //re-write file content, was unclear if we wanna keep origin in case of conflict
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Integer> readNumbersFromFile(String filename) {
        Path filePath = Paths.get("src\\assets\\"+filename);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("File does not exist");
            System.out.println(e.getMessage());
            return null;
        }
        List<Integer> numbersList = new ArrayList<>();
        for (String line : lines) {
            String[] numbers = line.split(","); // numbers must be split by comma in file, i can add some replace if necessary for other separators
            for (String numStr : numbers) {
                StringBuilder sb = new StringBuilder();
                for (char c : numStr.toCharArray()) {
                    if (Character.isDigit(c)) {
                        sb.append(c);
                    }
                }
                if (sb.length() > 0) {
                    int num = Integer.parseInt(sb.toString());
                    numbersList.add(num);
                }
            }
        }
        return numbersList;
    }
    public static List<Integer> processList(List<Integer> ilist){
        List<Integer> nmbrsToPrintSave = new ArrayList<>();
        if (ilist.size() % 2 == 0){
            for (int x : ilist){
                if (x % 2 == 0){
                    nmbrsToPrintSave.add(x);
                }
            }
        }else {
            for (int x : ilist){
                if (x % 2 == 1){
                    nmbrsToPrintSave.add(x);
                }
            }
        }
        return nmbrsToPrintSave;
    }
}