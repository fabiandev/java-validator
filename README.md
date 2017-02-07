# java-validator

A simple and extensible validation utility for Java.

# Usage

```java
import validator.contracts.Validator;
import validator.contracts.Bag;
import validator.core.StandardValidator;

Validator validator = new StandardValidator(request);

validator
    .rule("email", "max:35|email")
    .rule("phone", "required_unless:email|min:9|digits")
        .message("phone.min", ":field must be at least :min characters long.")
        .message("phone.digits", "For :field only digits are allowed")
    .rule("name", "required|min:6")
        .message("name.min", "Please fill in your name with at least :min characters.")
    .rule("tos_accepted", "required")
        .message("tos_accepted.required", "Please accept the Terms of Service.");

if (validator.fails()) {
    System.out.println("There were errors!");
    Bag errorBag = validator.errors();
    return;
}

System.out.println("Validation passed!");
```

# Extending

Find two basic examples of how to create custom rules below.

## Standard Rule

```java
import validator.core.BaseRule;

public class MyRule extends BaseRule {

  @Override
    protected void validate() {
    // Get the input field's value: this.inputField.getValue()
    // Get all input fields: this.inputFields
    // Get the rule parameter: this.ruleValue
    // Call this.fail() if validation doesn't pass
    }

    @Override
    protected String defaultMessage() {
        return ":field can include :my_rule placeholders for the parameter.";
    }

}
```

## Regex Rule

```java
import validator.rules.Regex;

public class Everything extends Regex {

    @Override
    protected String regex() {
        return "^.*$";
    }

    @Override
    protected String defaultMessage() {
        return ":field did not pass the everything rule.";
    }

}
```
