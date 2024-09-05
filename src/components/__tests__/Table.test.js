import { render, screen } from '@testing-library/react';
import Table from '../Table';

test('renders table with correct headers', () => {
  const columns = ['Nombre', 'Edad'];
  const data = [{ Nombre: 'Juan', Edad: 30 }];

  render(<Table columns={columns} data={data} />);
  expect(screen.getByText('Nombre')).toBeInTheDocument();
  expect(screen.getByText('Juan')).toBeInTheDocument();
});