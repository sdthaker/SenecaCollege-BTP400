import axios from 'axios';
import { useState, useEffect } from 'react';
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
  textAlign: 'center',
};

//validate if the input is a number
//validate if customer has enough money to withdraw
//validate if the amount is not greater than the balance

//calls a route in the backend server that requires currently
//logged in user's id, so Balance needs a currently logged in user's id
//to be passed in as props
export default function Withdraw(props) {
  const navigate = useNavigate();

  const [amount, setAmount] = useState(0.0);
  const [selectedAccountNo, setSelectedAccountNo] = useState(null);
  const [accountNumbers, setAccountNumbers] = useState([]);
  const [error, setError] = useState('');
  const [variant, setVariant] = useState('danger');

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
      setError('Please enter a number for withdrawal amount.');
    } else if (event.target[0].value < 0) {
      setVariant('danger');
      setError('Please enter a positive number for deposit amount.');
    } else {
      const balance = accountNumbers.find(
        ({ accountNumber }) => accountNumber === selectedAccountNo
      )?.balance;

      if (balance < event.target[0].value) {
        setVariant('danger');
        setError('Insufficient funds. Balance is $' + balance);
      } else {
        var config = {
          method: 'put',
          url: `http://localhost:8080/api/bankAccount/withdraw?acctNum=${selectedAccountNo}&amount=${amount}`,
          headers: {
            Authorization: props.user.access_token,
          },
        };

        axios(config)
          .then(function (response) {
            if (response.data) {
              setVariant('success');
              setError('Withdraw successful.');
            } else {
              setVariant('danger');
              setError(
                'Withdrawal unsuccessful, possibly because you dont have enough money in your account.'
              );
            }
          })
          .catch(function (error) {
            if (selectedAccountNo === null) {
              setVariant('danger');
              setError('Please select an account.');
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

                  setError('Withdrawal unsuccessful. Please try again.');
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
        <Form onSubmit={handleSubmit}>
          <Form.Group md='3'>
            <Form.Label style={{ fontSize: '25px', color: 'black' }}>
              Withdrawal Amount
            </Form.Label>
            <br />
            <br />
            <InputGroup>
              <Row style={{ paddingLeft: '15%' }}>
                <Col>
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
                  <br />
                </Col>
                <Col>
                  <DropdownButton
                    key='primary'
                    id={`dropdown-variants-secondary`}
                    variant='success'
                    title='Account number'
                    style={{ textAlign: 'right' }}
                  >
                    {accountNumbers.map(({ accountNumber }, index) => (
                      <Dropdown.Item
                        eventKey={accountNumber}
                        key={index}
                        active={selectedAccountNo === accountNumber}
                        onClick={(e) => {
                          setSelectedAccountNo(parseInt(e.target.innerText));
                        }}
                      >
                        {accountNumber}
                      </Dropdown.Item>
                    ))}
                  </DropdownButton>
                  {selectedAccountNo && <span style={{position: "absolute", transform: "translate(185px, -38px)"}} class="input-group-text" id="basic-addon1">{selectedAccountNo}</span>}
                </Col>
              </Row>
            </InputGroup>
          </Form.Group>
          {error && (
            <Alert
              style={{ width: '24rem', textAlign: 'center' }}
              className='m-auto'
              variant={variant}
            >
              {error}
            </Alert>
          )}
          <br />
          <Button variant='primary' type='submit' size='lg'>
            Withdraw
          </Button>{' '}
          &nbsp; &nbsp; &nbsp;
          <Button
            onClick={() => {
              navigate('/customer');
            }}
            variant='success'
            size='lg'
          >
            Go Back
          </Button>
        </Form>
      </Card>
    </Container>
  );
}
