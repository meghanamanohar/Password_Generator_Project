


import java.util.Random;
 enum PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}

//this will work as the backend and will generate the password
public class PasswordGenerator {

    //character pools
    //these strings will hold the characters/numbers/symbols that we are gonna randomly pick to generate our password 
    public static final String LOWERCASE_CHARACTERS="abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_CHARACTERS="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS="0123456789";
    public static final String SPECIAL_SYMBOLS="!#$%^&*()-_=[]{};:,.<>/?";

    //the random class allows us to generate a number which will be used to randomly choose the characters
    private final Random random;

    //constructor
    public PasswordGenerator()
    {
        random=new Random();//to generate random sequence 
    }

    //length : length of the password to be generated taken from the user
    //includeUppercase and etc...considers if the passwords shld include uppercase/lowercase etc
   
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeNumbers, boolean includeSpecialSymbols) {

        StringBuilder passwordBuilder = new StringBuilder();
        String validCharacters = "";

        if (includeUppercase) validCharacters += UPPERCASE_CHARACTERS;
        if (includeLowercase) validCharacters += LOWERCASE_CHARACTERS;
        if (includeNumbers) validCharacters += NUMBERS;
        if (includeSpecialSymbols) validCharacters += SPECIAL_SYMBOLS;


       int selectedCategoriesCount = 0;
        //build password;
        if (includeUppercase) {
            passwordBuilder.append(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));
            selectedCategoriesCount++;

        }
        if (includeLowercase) {
            passwordBuilder.append(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));
            selectedCategoriesCount++;
        }
        if (includeNumbers) {
            passwordBuilder.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
            selectedCategoriesCount++;
        }
        if (includeSpecialSymbols) {
            passwordBuilder.append(SPECIAL_SYMBOLS.charAt(random.nextInt(SPECIAL_SYMBOLS.length())));
            selectedCategoriesCount++;
        }
        if (length < selectedCategoriesCount) {
            // Inform the user and prompt them to select a longer password length
            return "Password length must be at least " + selectedCategoriesCount + " characters.";
        }



        for (int i = passwordBuilder.length(); i < length; i++) {
            int randomIndex = random.nextInt(validCharacters.length());
            char randomChar = validCharacters.charAt(randomIndex);
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }
    public PasswordStrength calculatePasswordStrength(String password) {
        int strengthScore = 0;

        // Password length contributes to strength
        int length = password.length();
        strengthScore += Math.min(length, 10) * 5; // Up to 50 points for length (max 10 characters)

        // Check for different character types to boost strength
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasNumbers = false;
        boolean hasSpecialSymbols = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasNumbers = true;
            } else {
                hasSpecialSymbols = true;
            }
        }

        int characterTypes = 0;
        if (hasLowercase) characterTypes++;
        if (hasUppercase) characterTypes++;
        if (hasNumbers) characterTypes++;
        if (hasSpecialSymbols) characterTypes++;

        strengthScore += characterTypes * 10; // Up to 40 points for character types

        // Final strength categorization
        if (strengthScore < 30) {
            return PasswordStrength.WEAK;
        } else if (strengthScore < 60) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.STRONG;
        }

    }

    
}














