package ua.university;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class InfoController {
    // Змінна, яка визначає, який текст показувати
    public static String viewType = "instructions";

    @FXML private Label titleLabel;
    @FXML private TextArea textArea;

    @FXML
    public void initialize() {
        if ("instructions".equals(viewType)) {
            titleLabel.setText("Інструкція користувача");
            textArea.setText("test test test instruction");
        } else if ("traditions".equals(viewType)) {
            titleLabel.setText("Традиції української вишивки");
            textArea.setText(
                    "• Давнє походження:\nДослідження свідчать, що вишите вбрання створювали ще у VI столітті нашої ери. На жаль, тканинні зразки вишивки з тих часів не збереглися.\n\n" +
                            "• Священний оберіг, а не повсякденність:\nУ давнину українці ніколи не носили вишиванок на щодень. Вишитий орнамент захищав людину від злих духів та мав глибоке сакральне значення. Для роботи використовували звичайні сорочки — «буденки».\n\n" +
                            "• Текстова вишивка (Бродівське письмо):\nЦе унікальна традиція кодування інформації в орнаменті. У вишивці існує спеціальний алфавіт, де кожен символ відповідає певній літері. Завдяки цьому на полотні можна було буквально «написати» ім'я чи зашифрувати побажання.\n\n" +
                            "• Перший «модний інфлюенсер»:\nПершою людиною, яка вивела вишиванку з контексту суто народного одягу і зробила її елементом повсякденного міського стилю, був Іван Франко. Він почав сміливо одягати її під класичний європейський піджак."
            );
        }
    }

    @FXML
    private void goBack() {
        Main.setRoot("/menu.fxml"); // Повертаємось у меню
    }
}