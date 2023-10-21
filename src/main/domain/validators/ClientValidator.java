package main.domain.validators;

import main.domain.Client;


public class ClientValidator implements Validator<Client>  {

    @Override
    public void validate(Client entity) throws ValidatorException {

        if (entity.getCNP().length() != 13){
            throw new ValidatorException("The CNP is not valid. It must have only 13 numbers! ");
        }
        if (!entity.getCNP().matches("[0-9]+")){
            throw new ValidatorException("The CNP is not valid. It must have only numbers! ");
        }

        if (entity.getLastName().length() < 2){
            throw new ValidatorException("The last name must have at least 2 letters! ");
        }
        if (entity.getFirstName().length() < 2){
            throw new ValidatorException("The first name must have at least 2 letters! ");
        }
    }
}
