# Project Description: Registration API
บริษัทต้องการ Web Application ส าหรับให้ลูกค้าทั่วไปสมัครใช้บริการประกันภัย
โดย Web Application ต้องท างานเบื้องต้นได้ดังนี้
- สร้างใบสมัครประกัน รายละเอียดชื่อที่อยู่ เบอร์โทรศัพท์ รูปถ่าย
- รูปถ่ายต้องท าการเก็บบน Cloud (AWS)
- สามารถแก้ไข รายละเอียดของผู้สมัคร และยกเลิกการสมัครได้
-approve/reject รายการที่สมัครเข้ามา

สิ่งที่ผู้สมัครในฐานะ Senior Java Developerจะต้องท าคือ พัฒนา Backend Service APIs ส าหรับ Web
Application และน า Application ขึ้น Cloud ได้
- ### 1.สร้าง Project โดยใช้ Spring Boot และมี Elasticsearch เป็น Database ส าหรับรองรับการ ทำงานของ Web Application

```
This project is an API service

It is using **Java 21**, **Spring Boot**, and other dependencies can be found in the `pom.xml` file

https://github.com/A2A2-personal/mars-test/tree/develop

The API service connects to **Amazon OpenSearch Service** (an Elasticsearch engine).  

If you would like to test it, `ช่วนส่ง IP address ให้ผมหน่อยนะครับ`.

And

a frontend service using **React** and **React Router**, running on **Node.js version 18**.

https://github.com/A2A2-personal/mars-test-frontend/tree/develop

start service step

npm install
npm run dev
```
---
- ### 2.สร้าง Dockerfile สำหรับ Build pack project เป็น Docker

```
You can find dockerfile in root project ./Dockerfile
```
---

- ### 3.สร้าง Helm template เพื่อเตรียมส าหรับน า Project นี้ขึ้น Kubernetes โดยเก็บรายละเอียด
Config ของ app ไว้ที่ configmap


```
ยังไม่เคยใช้ครับ
```

---

- ### 4. แสดงขั้นตอนการนำระบบขึ้น Production (ด้วย Tool ใดๆก็ได้)

```
ปกติผมจะใช้ azure pipeline เอาขึ้นเพิ่มทำการ build image แล้วเอา image ขึ้น azure container registry (จบ CI)

หลังจากนั้นก็ใช้ตัว auzre pipeline release จะไป apply deployment, apply service (kube apply) แก้ kube apply yaml ให้ไปดึง image จาก azure container registry แล้วก็ apply ไปที่ kube (จบ CD)
```
---

- ### 5.แสดง Architecture Diagram ของ Production ด้วย Components ที่คิดว่าเกี่ยวข้องกับระบบนี้ทั้งหมด


** note 
```
อันนี้ผมยังไม่เคย deploy ขึ้น aws จริงๆ (ทำไมทันครับ) แต่ไปลองสร้าง kube cluster ของ AWS, elasticsearch
(ใน diagram ตัว elasticsearch จะเป็น private ตอนที่ผมลอง เพราะเหมือนจะออกเน็ตได้ ของ aws น่าจะใช้ API gateway,
หรือถ้าใช้ E2C จะ run nginx เป็น revert proxy ถ้าจำไม่ผิดจากที่เคย ec2 instance จะได้ dns public มาเลย)



ส่วนเรื่อง `- รูปถ่ายต้องทำการเก็บบน Cloud (AWS)` ผมสร้าง API /registration รับเป็น formData submit มาแล้วผมก็เก็บลง path ./uploads
แล้วจังหวะ Apply ค่อยเข้าไป storage mapping กับ Cloud storage (ยังไม่เคยใช้ Cloud storage ของ AWS ครับ)

```



![mars-test](https://github.com/user-attachments/assets/d6c315db-6df8-470e-a796-a9622e050bdf)


---
