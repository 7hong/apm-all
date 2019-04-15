import axios from '@/libs/api.request'

const apps = () => {
  return axios.request({
    url: '/index/apps', method: 'get'
  })
}

const topData = () => {
  return axios.request({
    url: '/top', method: 'get'
  })
}

const methods = (app) => {
  return axios.request({
    url: '/index/methods?app=' + app, method: 'get'
  })
}

const mem_metric = (f) => {
  return axios.request({
    url: '/chart/mem_metric', method: 'get', params: f
  })
}

const thread_metric = (f) => {
  return axios.request({
    url: '/chart/thread_metric', method: 'get', params: f
  })
}

const class_metric = (f) => {
  return axios.request({
    url: '/chart/class_metric', method: 'get', params: f
  })
}

const gc_metric = (f) => {
  return axios.request({
    url: '/chart/gc_metric', method: 'get', params: f
  })
}

const method_metric = (f) => {
  return axios.request({
    url: '/chart/method_metric', method: 'get', params: f
  })
}

export {
  apps,
  methods,
  method_metric,
  thread_metric,
  gc_metric,
  mem_metric,
  class_metric,
  topData
}

export const getTableData = () => {
  return axios.request({
    url: 'get_table_data',
    method: 'get'
  })
}

export const getDragList = () => {
  return axios.request({
    url: 'get_drag_list',
    method: 'get'
  })
}

export const errorReq = () => {
  return axios.request({
    url: 'error_url',
    method: 'post'
  })
}

export const saveErrorLogger = info => {
  return axios.request({
    url: 'save_error_logger',
    data: info,
    method: 'post'
  })
}

export const uploadImg = formData => {
  return axios.request({
    url: 'image/upload',
    data: formData
  })
}

export const getOrgData = () => {
  return axios.request({
    url: 'get_org_data',
    method: 'get'
  })
}

export const getTreeSelectData = () => {
  return axios.request({
    url: 'get_tree_select_data',
    method: 'get'
  })
}
