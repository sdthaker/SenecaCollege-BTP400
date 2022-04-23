import axios from 'axios';

import React, { useState, useEffect } from 'react';
import {
  Row,
  Col,
  Form,
  InputGroup,
  Button,
  Container,
  Card,
  DropdownButton,
  Dropdown,
  Alert,
} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const paperStyle = {
  padding: '50px 20px',
  width: 600,
  margin: '20px auto',
  backgroundColor: '#9fa09e',
};

const styleForHorizontalCenter = {
  position: 'absolute',
  top: '50%',
  transform: 'translateY(-50%)',
};

//validate the input
//validate if the 'from' bank account has enough money to transfer else show error
export default function Transfer(props) {
  const navigate = useNavigate();

  const [amount, setAmount] = useState(0.0);
  const [accountNumbers, setAccountNumbers] = useState([]);
  const [error, setError] = useState('');
  const [variant, setVariant] = useState('danger');
  const [from, setFrom] = useState(null);
  const [to, setTo] = useState(null);

  useEffect(() => {
    if (props.user.id === 0) {
      navigate('/');
      localStorage.setItem('isAuthenticated', false);
    }
  }, [props.user]);

  const handleSubmit = (event) => {
    event.preventDefault();

    if (isNaN(event.target[0].value)) {
      setVariant('danger');
      setError('Please enter a number for transfer amount.');
    } else if (event.target[0].value < 0) {
      setVariant('danger');
      setError('Please enter a positive number for deposit amount.');
    } else {
      if (from === to) {
        setVariant('danger');
        setError('Please select different account numbers.');
      } else {
        const fromBalance = accountNumbers.find(
          ({ accountNumber }) => accountNumber === from
        )?.balance;

        if (fromBalance < amount) {
          setVariant('danger');
          setError('Insufficient funds. Balance is $' + fromBalance);
        } else {
          var config = {
            method: 'put',
            url: `http://localhost:8080/api/bankAccount/transfer?from=${from}&to=${to}&amount=${amount}`,
            headers: {
              Authorization: props.user.access_token,
            },
          };

          axios(config)
            .then(function (response) {
              if (response.data) {
                setVariant('success');
                setError('Transfer successful.');
              } else {
                setVariant('danger');
                setError(
                  "Transfer unsuccessful, possibly because you dont have enough money in the 'from' account."
                );
              }
            })
            .catch(function (error) {
              if (to === null) {
                setVariant('danger');
                setError("Please select an account for 'To'.");
              } else if (from === null) {
                setVariant('danger');
                setError("Please select an account for 'From'.");
              } else if (error.response.status === 403) {
                var config = {
                  method: 'get',
                  url: 'http://localhost:8080/api/token/refresh',
                  headers: {
                    Authorization: props.user.refresh_token,
                  },
                  data: '',
                };

                axios(config)
                  .then(function (response) {
                    let updateUser = props.user;
                    updateUser.access_token = response.data.access_token;
                    updateUser.refresh_token = response.data.refresh_token;

                    props.setUser(updateUser);

                    setError('Deposit unsuccessful. Please try again.');
                    setVariant('danger');
                  })
                  .catch(function (error) {
                    console.log(error);

                    navigate('/');
                    localStorage.setItem('isAuthenticated', false);
                  });
              } else {
                setError('API error.');
                setVariant('danger');
              }
            });
        }
      }
    }
  };

  //the moment the component gets mounted
  //api should be called which respond with an array of bank account numbers +
  //balance for the each of the bank account number
  useEffect(() => {
    var config = {
      method: 'get',
      url: `http://localhost:8080/api/bankAccount/getAllBankAccount?userId=${props.user.id}`,
      headers: {
        Authorization: props.user.access_token,
      },
      data: '',
    };

    axios(config)
      .then(function (response) {
        if (response.data.length === 0) {
          setVariant('danger');
          setError('You do not have any bank accounts.');
        } else {
          setAccountNumbers(response.data);
        }
      })
      .catch(function (error) {
        if (error.response.status === 403) {
          var config = {
            method: 'get',
            url: 'http://localhost:8080/api/token/refresh',
            headers: {
              Authorization: props.user.refresh_token,
            },
            data: '',
          };

          axios(config)
            .then(function (response) {
              let updateUser = props.user;
              updateUser.access_token = response.data.access_token;
              updateUser.refresh_token = response.data.refresh_token;

              props.setUser(updateUser);

              setError('Token expired. Please try again.');
              setVariant('danger');
            })
            .catch(function (error) {
              console.log(error);

              navigate('/');
              localStorage.setItem('isAuthenticated', false);
            });
        } else {
          console.log(error);
          setVariant('danger');
          setError('Error in API Call.');
        }
      });
  }, []);

  return (
    <Container fluid style={{ ...styleForHorizontalCenter }}>
      <Card style={{ ...paperStyle }}>
        <Alert
          variant='success'
          style={{
            height: '3rem',
          }}
          className='m-auto'
        >
          <Alert.Heading style={{ marginTop: '-7px' }}>
            Hello {props.user.firstName} {props.user.lastName}!
          </Alert.Heading>
        </Alert>
        <br></br>
        <Row className='m-auto'>
          <Col style={{}}>
            <DropdownButton
              key='primary'
              id={`dropdown-variants-secondary`}
              variant='success'
              title='From'
              size='lg'
              style={{ textAlign: 'right' }}
            >
              {accountNumbers.map(({ accountNumber }, index) => (
                <Dropdown.Item
                  eventKey={index}
                  key={index}
                  active={from === accountNumber}
                  onClick={(e) => {
                    setFrom(parseInt(e.target.innerText));
                    setError('');
                  }}
                >
                  {accountNumber}
                </Dropdown.Item>
              ))}
            </DropdownButton>
            {from && <span style={{position: "absolute", transform: "translate(-46px, -42px)"}} class="input-group-text" id="basic-addon1">{from}</span>}
          </Col>
          <Col>
            <DropdownButton
              key='primary'
              id={`dropdown-variants-secondary`}
              variant='success'
              title='To'
              size='lg'
              style={{ textAlign: 'right' }}
            >
              {accountNumbers.map(({ accountNumber }, index) => (
                <Dropdown.Item
                  eventKey={index}
                  key={index}
                  active={to === accountNumber}
                  onClick={(e) => {
                    setTo(parseInt(e.target.innerText));
                    setError('');
                  }}
                >
                  {accountNumber}
                </Dropdown.Item>
              ))}
            </DropdownButton>
            {to && <span style={{position: "absolute", transform: "translate(85px, -42px)"}} class="input-group-text" id="basic-addon1">{to}</span>}
          </Col>
        </Row>
        <br></br>
        <Form onSubmit={handleSubmit} className='m-auto'>
          <Row>
            <Form.Group md='3' style={{ textAlign: 'center' }}>
              <Form.Label style={{ fontSize: '25px', color: 'black' }}>
                Transfer Amount
              </Form.Label>
              <InputGroup hasValidation>
                <div style={{ width: '20rem' }}>
                  <Form.Control
                    type='text'
                    placeholder='0.00'
                    aria-describedby='inputGroupPrepend'
                    required
                    value={amount}
                    onChange={(e) => {
                      setAmount(e.target.value);
                      setError('');
                    }}
                  />
                </div>
              </InputGroup>
            </Form.Group>
          </Row>
          &nbsp;
          {error && (
            <Alert
              style={{ width: '20rem', textAlign: 'center' }}
              className='m-auto'
              variant={variant}
            >
              {error}
            </Alert>
          )}
          <br />
          <Row>
            <Button
              variant='primary'
              type='submit'
              size='lg'
              style={{ width: '10rem' }}
            >
              Transfer
            </Button>{' '}
            &nbsp; &nbsp; &nbsp;
            <Button
              onClick={() => navigate('/customer')}
              variant='success'
              size='lg'
              style={{ width: '10rem' }}
            >
              Go Back
            </Button>
          </Row>
        </Form>
      </Card>
    </Container>
  );
}
