<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <!-- background image -->
  <div class="admin-img" style="display:block;">
  </div>
<div class="container col-md-8 mt-5">
  <b-tabs content-class="mt-3" fill>
    <!-- 포스트 관리 -->
    <b-tab class="admin-post" title="포스트 관리" active>
    <table class="table my-5">
      <thead class="thead-light">
        <tr>
          <th class="admin-post-num">No</th>
          <th class="admin-post-title">제목</th>
          <th class="admin-post-company">업체정보</th>
          <th class="admin-post-email">작성자</th>
          <th class="admin-post-date">요청 날짜</th>
          <th class="admin-post-out">승인 및 거절</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(post,index) in postlists" :key="index">
              <td>{{index+1}}</td>
              <td class="admin-data">{{post.title}}</td>
              <td class="admin-data">{{post.companyInfo}}</td>
              <td class="admin-data">{{post.email}}</td>
              <td class="admin-data">{{permitdate(post.createDate)}}</td>
              <td><button class="admin-post-button" @click="gopostdetail(post.pid)">상세보기</button></td>
              <!-- <td><button @click="postlistData(post)" data-toggle="modal" data-target="#postpermit">상세보기</button></td> -->
              <!-- <td><button @click="postpermit(post.pid)">승인</button> <button @click="postreject(post.pid)">거절</button></td> -->
          </tr>
      </tbody>
    </table>
    <!-- <PostPermitDetailModal :post="postlistDataReceive"/> -->
    </b-tab>

    <!-- 회원 관리 -->
    <b-tab class="admin-info" title="회원관리">
    <table class="table my-5">
      <thead class="thead-light">
        <tr>
          <th class="admin-info-num">No</th>
          <th class="admin-info-email">이메일</th>
          <th class="admin-info-nickname">닉네임</th>
          <th class="admin-info-name">이름</th>
          <th class="admin-info-type">사용자 유형</th>
          <th class="admin-info-out">탈퇴</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(user,index) in users" :key="index">
                <td v-if="user.uid!=1">{{index}}</td>
                <td class="admin-data" v-if="user.uid!=1">{{user.email}}</td>
                <td class="admin-data" v-if="user.uid!=1">{{user.nickname}}</td>
                <td class="admin-data" v-if="user.uid!=1">{{user.name}}</td>
                <td class="admin-data" v-if="user.uid!=1">{{user.checkType}}</td>
                <td v-if="user.uid!=1"><button class="admin-info-button" @click="dropuser(user.uid)">탈퇴</button></td>
          </tr>
      </tbody>
    </table>
    </b-tab>

    <!-- 신고 관리 -->
    <b-tab title="신고 관리" ><p></p>
    <table class="table my-5">
      <thead class="thead-light">
        <tr>
          <th class="admin-black-num">No</th>
          <th class="admin-black-email">신고한사람</th>
          <th class="admin-black-remail">신고당한사람</th>
          <th class="admin-black-content">내용</th>
          <th class="admin-black-detail">상세보기</th>
          <th class="admin-black-out">취소/경고/탈퇴</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(blacklist, index) in blacklists" :key="index">
              <td>{{index+1}}</td>
              <td class="admin-data">{{blacklist.email}}</td>
              <td class="admin-data">{{blacklist.remail}}</td>
              <td class="admin-data">{{blacklist.reason}}</td>
              <td class="admin-data"><button class="admin-black-view" @click="blacklistData(blacklist)" data-toggle="modal" data-target="#blacklist">상세보기</button></td>
              <td><button class="admin-black-cancel" @click="cancelblack(blacklist.rpid)">거절</button><button class="admin-black-warn mr-1" @click="warnblack(blacklist.rpid)">경고</button><button class="admin-black-drop mr-1" @click="dropblack(blacklist.rpid)">탈퇴</button></td>
          </tr>
      </tbody>
    </table>
            <BlackListDetailModal :blacklist="blacklistDataReceive" @warn-black="warnblack" @drop-black="dropblack" @cancel-black="cancelblack"/>
    </b-tab>
    
  </b-tabs>
</div>
</div>
</template>

<script>
import axios from 'axios';
import '../../assets/css/admin.css';
import BlackListDetailModal from '../../components/modal/BlackListDetailModal.vue'
// import PostPermitDetailModal from '../../components/modal/PostPermitDetailModal.vue'

const baseURL = process.env.VUE_APP_BACKURL;

export default {
    components:{
      BlackListDetailModal,
      // PostPermitDetailModal,
    },
    data(){
        return{
          postlists:{},
          users:{},
          blacklists:{},
          blacklistDataReceive:{},
          postlistDataReceive:{},
          hashTag:[],
            springCheck: "",
            summerCheck: "",
            autumnCheck: "",
            winterCheck: "",
            placeCheck: "",
        }
    },
    created(){
      this.postlist();
      this.userlist();
      this.blackuser();
    },
    methods:{
      postlist(){
        axios
          .get(`${baseURL}/post/standpost`)
          .then((res)=>{
              this.postlists = res.data
          })
          .catch((err)=>{
              console.log(err)
          })
      },
      userlist(){
        axios
          .get(`${baseURL}/account/viewAllUser`)
          .then((res)=>{
              this.users = res.data
              // console.log(this.users)
          })
          .catch((err)=>{
              console.log(err)
          })
        },
      blackuser(){
        axios
          .get(`${baseURL}/report/list`)
          .then((res)=>{
            this.blacklists = res.data
          }).catch((err)=>{
            console.log(err)
          })
      },
      cancelblack(rpid){
        axios
          .delete(`${baseURL}/report/cancel/${rpid}`)
          .then(()=>{
            this.blackuser()
          }).catch((err)=>{
            console.log(err)
          })
      },

      dropuser(uid){
        axios
          .delete(`${baseURL}/account/dropUser/${uid}`)
          .then(()=>{
            this.userlist()
          }).catch((err)=>{
            console.log(err)
          })
      },
      dropblack(rpid){
        axios
          .delete(`${baseURL}/report/dropUser/${rpid}`)
          .then(()=>{
            this.blackuser()
          }).catch((err)=>{
            console.log(err)
          })
      },
      warnblack(rpid){
        axios
          .get(`${baseURL}/report/warnUser/${rpid}`)
          .then(()=>{
            this.blackuser()
          }).catch((err)=>{
            console.log(err)
          })
      },
      blacklistData(blacklist) {
        this.blacklistDataReceive = blacklist
      }, 
      permitdate(createDate){
        var pd = createDate+'';
        return pd.substring(0,10)+" "+pd.substring(11,19)
      },
      gopostdetail(pid){
        scroll(0, 0);
        this.$router.push({
        name: 'AdminPostDetail',
        params: { ID: pid },
      });
      },

    //   postlistData(post){
    //     this.postlistDataReceive = post
    //     this.getPost(post.pid);
    //     this.fetchHashTag(post.pid);
    //   },
    //   getPost(pid) {
    //   axios
    //     .get(`${baseURL}/post/detail/${pid}`)
    //     .then((res) => {
    //       this.springCheck = res.data.spring;
    //       this.summerCheck = res.data.summer;
    //       this.autumnCheck = res.data.autumn;
    //       this.winterCheck = res.data.winter;
    //       this.placeCheck = res.data.place;
    //       this.mapView(post.location);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
    //     mapView(loc) {
    //   var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    //     mapOption = {
    //       center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    //       level: 5, // 지도의 확대 레벨
    //     };

    //   // 지도를 생성합니다
    //   var map = new kakao.maps.Map(mapContainer, mapOption);

    //   // 주소-좌표 변환 객체를 생성합니다
    //   var geocoder = new kakao.maps.services.Geocoder();

    //   // 주소로 좌표를 검색합니다
    //   geocoder.addressSearch(loc, function (result, status) {
    //     // 정상적으로 검색이 완료됐으면
    //     if (status === kakao.maps.services.Status.OK) {
    //       // console.log(result);
    //       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

    //       // 결과값으로 받은 위치를 마커로 표시합니다
    //       var marker = new kakao.maps.Marker({
    //         map: map,
    //         position: coords,
    //       });
    //       var test = loc;

    //       // 인포윈도우로 장소에 대한 설명을 표시합니다
    //       var infowindow = new kakao.maps.InfoWindow({
    //         content: test,
    //       });
    //       infowindow.open(map, marker);

    //       // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    //       map.setCenter(coords);
    //     }
    //   });
    // },
    // fetchHashTag(pid) {
    //   axios
    //     .get(`${baseURL}/tag/list/${pid}`)
    //     .then((response) => {
    //       this.hashTag = response.data;
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },

    // 승인
    postpermit(pid){
      axios
        .get(`${baseURL}/post/permit/${pid}`)
        .then((res)=>{
          console.log(res.data)
          this.postlist()
        }).catch((err)=>{
          console.log(err)
        })
    },

    // 거절
    postreject(pid){
      axios
        .delete(`${baseURL}/post/delete/${pid}`)
        .then(()=>{
          this.postlist()
      }).catch((err)=>{
        console.log(err)
      })
    },


    },

}
</script>

<style>

</style>