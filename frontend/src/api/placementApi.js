import axiosInstance from './axiosInstance';

export const getPlacements = () => axiosInstance.get('/placements');
export const createPlacement = (data) => axiosInstance.post('/placements', data);
export const serveBanner = (id) => axiosInstance.get(`/placements/${id}/serve`);
