import com.fasterxml.jackson.databind.ObjectMapper;
import model.Row;
import model.Stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> rowsString = Files.readAllLines(Paths.get("resources", "table-indicateurs-open-data-france.csv"));

        // Remove first row (header)
        rowsString.remove(0);

        List<Row> rows = new ArrayList<>();

        for (String rowString : rowsString) {
            String[] values = rowString.split(",");
            LocalDate extractDate = LocalDate.parse(values[0].replace("\"", ""));
            Double incidenceRate = getValue(values[1]);
            Double r = getValue(values[2]);
            Double occupancyRate = getValue(values[3]);
            Double positivityRate = getValue(values[4]);

            rows.add(new Row(extractDate, incidenceRate, r, occupancyRate, positivityRate));
        }

        // Moyenne du taux de positivité
        double averagePositivityRate = rows.stream()
            .filter(row -> row.getPositivityRate() != null)
            .mapToDouble(row -> row.getPositivityRate())
            .average()
            .orElse(0);

        // Les lignes qui ont des données pour chaque indicateur (colonne)
        List<Row> fullRows = rows.stream()
            .filter(row ->
                row.getExtractDate() != null && row.getR() != null &&
                    row.getPositivityRate() != null && row.getIncidenceRate() != null &&
                    row.getOccupancyRate() != null
            )
            .collect(Collectors.toList());

        // Les dates auxquelles le R était > 1.45
        List<LocalDate> datesWithHighR = rows.stream()
            .filter(row -> row.getR() != null)
            .filter(row -> row.getR() > 1.45)
            .map(row -> row.getExtractDate())
            .collect(Collectors.toList());

        // Les 10 jours avec le taux d'incidence le plus élevé
        List<LocalDate> highestIncidenceRate = rows.stream()
            .filter(row -> row.getIncidenceRate() != null)
            .sorted(Comparator.comparingDouble(row -> -row.getIncidenceRate()))
            .map(row -> row.getExtractDate())
            .limit(10)
            .collect(Collectors.toList());

        // Moyenne d'occupation par mois
        Map<Month, Double> averageOccupancyByMonth = rows.stream()
            .filter(row -> row.getOccupancyRate() != null)
            .collect(
                Collectors.groupingBy(
                    row -> row.getExtractDate().getMonth(),
                    Collectors.averagingDouble(row -> row.getOccupancyRate())
                )
            );

        // Création de l'objet de sortie
        Stats stats = new Stats();
        stats.setAveragePositivityRate(averagePositivityRate);
        stats.setDatesWithHighR(datesWithHighR);
        stats.setFullRows(fullRows);
        stats.setHighestIncidenceRate(highestIncidenceRate);
        stats.setOccupancyByMonth(averageOccupancyByMonth);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(Files.newOutputStream(Paths.get("out.json")), stats);
    }

    // Méthode pour convertir les valeurs (sous forme de String) présentes le fichier
    // Une valeur NA est mappée en null côté Java
    private static Double getValue(String value) {
        if ("NA".equals(value)) {
            return null;
        }

        return Double.valueOf(value);
    }
}
