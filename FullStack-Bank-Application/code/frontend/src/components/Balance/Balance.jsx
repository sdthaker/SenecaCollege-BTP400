import axios from 'axios';
import { useState, useEffect } from 'react';
import { Row, Col, Button, Card, Container, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

//fetch the balance from the server and display it
//a customer can have more than one account

//calls a route in the backend server that requires currently
//logged in user's id, so Balance needs a currently logged in user's id
//to be passed in as props
export default function Balance(props) {
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [variant, setVariant] = useState('danger');

  const [customerData, setCustomerData] = useState([]);

  useEffect(() => {
    if (props.user.id === 0) {
      navigate('/');
      localStorage.setItem('isAuthenticated', false);
    }
  }, [props.user]);

  const styleForHorizontalCenter = {
    position: 'relative',
    top: '50%',
    transform: 'translateY(10%)',
    textAlign: 'center',
    width: '18rem',
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
          setCustomerData(response.data);
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
    <Container fuild='true' style={{ ...styleForHorizontalCenter }}>
      <Row>
        <Col>
          {error && <Alert variant='danger'>{error}</Alert>}
          {customerData.map(({ accountNumber, balance }, index) => (
            <Card key={index}>
              <Card.Body>
                <Card.Title style={{ color: 'black' }}>
                  Account Number: <strong>{accountNumber}</strong>
                </Card.Title>
                <Card.Text style={{ color: 'black' }}>
                  Balance: <strong>${balance}</strong>
                </Card.Text>
              </Card.Body>
            </Card>
          ))}
          <br />
          <Button
            variant='success'
            size='lg'
            onClick={() => {
              navigate('/customer');
            }}
          >
            Go Back
          </Button>
        </Col>
      </Row>
    </Container>
  );
}
