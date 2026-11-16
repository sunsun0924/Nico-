
/* ========================================
   初期設定
======================================== */

document.addEventListener("DOMContentLoaded", function () {

    const mainElement = document.querySelector(".home-header");

    if (!mainElement) {
        return;
    }

    const contextPath = mainElement.dataset.contextPath || "";


    /* ========================================
       三点メニューの開閉
    ======================================== */

    document.addEventListener("click", function (event) {

        const menuButton = event.target.closest(".menu-button");

        if (menuButton) {

            event.stopPropagation();

            const dropdown = menuButton.nextElementSibling;

            document.querySelectorAll(".menu-dropdown").forEach(menu => {

                if (menu !== dropdown) {
                    menu.classList.remove("show");
                }

            });

            if (dropdown) {
                dropdown.classList.toggle("show");
            }

            return;
        }

        // メニュー外をクリックしたとき
        if (!event.target.closest(".menu-dropdown")) {

            document.querySelectorAll(".menu-dropdown").forEach(menu => {
                menu.classList.remove("show");
            });

        }

    });


    /* ========================================
       削除処理
    ======================================== */

    const deleteModal = document.getElementById("deleteModal");

    const deleteConfirmButton =
        document.getElementById("deleteConfirmButton");

    const deleteCancelButton =
        document.getElementById("deleteCancelButton");

    let deleteType = "";
    let deleteId = "";


    // 削除ボタン
    document.addEventListener("click", function (event) {

        const button = event.target.closest(".delete-button");

        if (!button) {
            return;
        }

        event.stopPropagation();

        deleteType = button.dataset.deleteType;

        if (deleteType === "post") {

            deleteId = button.dataset.postId;

        } else if (deleteType === "comment") {

            deleteId = button.dataset.commentId;

        } else if (deleteType === "announcement") {

            deleteId = button.dataset.announcementId;

        }

        document.querySelectorAll(".menu-dropdown").forEach(menu => {
            menu.classList.remove("show");
        });

        if (deleteModal) {
            deleteModal.classList.add("show");
        }

    });


    // 削除確定
    if (deleteConfirmButton) {

        deleteConfirmButton.addEventListener("click", function () {

            if (!deleteType || !deleteId) {
                return;
            }

            const form = document.createElement("form");

            form.method = "post";

            const idInput = document.createElement("input");

            idInput.type = "hidden";

            if (deleteType === "post") {

                form.action =
                    contextPath + "/servlet.PostDeleteServlet.action";

                idInput.name = "postId";

            } else if (deleteType === "comment") {

                form.action =
                    contextPath + "/servlet.CommentDeleteServlet.action";

                idInput.name = "commentId";

            } else if (deleteType === "announcement") {

                form.action =
                    contextPath + "/servlet.AnnouncementDeleteServlet.action";

                idInput.name = "announcementId";

            }

            idInput.value = deleteId;

            form.appendChild(idInput);

            document.body.appendChild(form);

            form.submit();

        });

    }


    // 削除キャンセル
    function closeDeleteModal() {

        if (deleteModal) {
            deleteModal.classList.remove("show");
        }

        deleteType = "";
        deleteId = "";

    }

    if (deleteCancelButton) {
        deleteCancelButton.addEventListener("click", closeDeleteModal);
    }

    if (deleteModal) {

        deleteModal.addEventListener("click", function (event) {

            if (event.target === this) {
                closeDeleteModal();
            }

        });

    }


    /* ========================================
       編集処理
    ======================================== */

    document.addEventListener("click", function (event) {

        const button = event.target.closest(".edit-button");

        if (!button) {
            return;
        }

        event.stopPropagation();

        const type = button.dataset.editType;
        const text = button.dataset.editText;

        document.querySelectorAll(".menu-dropdown").forEach(menu => {
            menu.classList.remove("show");
        });


        // 投稿編集
        if (type === "post") {

            const idElement = document.getElementById("editPostId");
            const textElement = document.getElementById("editPostText");
            const modal = document.getElementById("postEditModal");

            if (idElement && textElement && modal) {

                idElement.value = button.dataset.postId;
                textElement.value = text;

                modal.classList.add("show");

            }

        }


        // コメント編集
        else if (type === "comment") {

            const idElement = document.getElementById("editCommentId");
            const textElement = document.getElementById("editCommentText");
            const modal = document.getElementById("commentEditModal");

            if (idElement && textElement && modal) {

                idElement.value = button.dataset.commentId;
                textElement.value = text;

                modal.classList.add("show");

            }

        }


        // お知らせ編集
        else if (type === "announcement") {

            const idElement = document.getElementById("editAnnouncementId");
            const textElement = document.getElementById("editAnnouncementText");
            const modal = document.getElementById("announcementEditModal");

            if (idElement && textElement && modal) {

                idElement.value = button.dataset.announcementId;
                textElement.value = text;

                modal.classList.add("show");

            }

        }

    });


    /* ========================================
       編集モーダルを閉じる
    ======================================== */

    document.querySelectorAll("[data-close-modal]").forEach(button => {

        button.addEventListener("click", function () {

            const modalId = this.dataset.closeModal;

            const modal = document.getElementById(modalId);

            if (modal) {
                modal.classList.remove("show");
            }

        });

    });


    // モーダル外側をクリック
    document.querySelectorAll(".edit-modal").forEach(modal => {

        modal.addEventListener("click", function (event) {

            if (event.target === this) {
                this.classList.remove("show");
            }

        });

    });


    /* ========================================
       お知らせ自動スライド
       ・4秒ごとに切り替え
       ・最後から最初に戻る
       ・左右の矢印で操作可能
       ・数字は表示しない
    ======================================== */

    const slider = document.querySelector(".announcement-slider");

    if (slider) {

        const viewport = slider.querySelector(".announcement-viewport");
        const track = slider.querySelector(".announcement-track");

        const prevButton = slider.querySelector(".announcement-prev");
        const nextButton = slider.querySelector(".announcement-next");

        const originalItems = Array.from(
            track.querySelectorAll(".announcement-item")
        );

        const itemCount = originalItems.length;

        // お知らせが0件または1件ならスライド不要
        if (itemCount <= 1) {

            if (prevButton) {
                prevButton.style.display = "none";
            }

            if (nextButton) {
                nextButton.style.display = "none";
            }

        } else {

            // 最後と最初の複製を作成してループを自然にする
            const firstClone = originalItems[0].cloneNode(true);
            const lastClone = originalItems[itemCount - 1].cloneNode(true);

            firstClone.classList.add("announcement-clone");
            lastClone.classList.add("announcement-clone");

            track.appendChild(firstClone);
            track.insertBefore(lastClone, track.firstChild);

            const allItems = track.querySelectorAll(".announcement-item");

            let currentIndex = 1;
            let slideTimer = null;
            let isTransitioning = false;

            const slideDuration = 4000;

            // 最初の本物のお知らせを表示
            function updateSlide(animate = true) {

                track.style.transition = animate
                    ? "transform 0.5s ease"
                    : "none";

                track.style.transform =
                    `translateX(-${currentIndex * 100}%)`;

            }

            updateSlide(false);


            // 次のお知らせ
            function nextSlide() {

                if (isTransitioning) {
                    return;
                }

                isTransitioning = true;

                currentIndex++;

                updateSlide(true);

            }


            // 前のお知らせ
            function prevSlide() {

                if (isTransitioning) {
                    return;
                }

                isTransitioning = true;

                currentIndex--;

                updateSlide(true);

            }


            // スライド終了後の処理
            track.addEventListener("transitionend", function () {

                // 最後の複製から最初の本物へ
                if (currentIndex === itemCount + 1) {

                    currentIndex = 1;

                    updateSlide(false);

                }

                // 最初の複製から最後の本物へ
                else if (currentIndex === 0) {

                    currentIndex = itemCount;

                    updateSlide(false);

                }

                isTransitioning = false;

            });


            // 自動スライド開始
            function startAutoSlide() {

                stopAutoSlide();

                slideTimer = setInterval(function () {
                    nextSlide();
                }, slideDuration);

            }


            // 自動スライド停止
            function stopAutoSlide() {

                if (slideTimer !== null) {

                    clearInterval(slideTimer);

                    slideTimer = null;

                }

            }


            // 次へボタン
            if (nextButton) {

                nextButton.addEventListener("click", function () {

                    nextSlide();

                    startAutoSlide();

                });

            }


            // 前へボタン
            if (prevButton) {

                prevButton.addEventListener("click", function () {

                    prevSlide();

                    startAutoSlide();

                });

            }


            // マウスを乗せている間は自動スライド停止
            slider.addEventListener("mouseenter", stopAutoSlide);

            slider.addEventListener("mouseleave", startAutoSlide);


            // キーボード操作中も自動スライド停止
            slider.addEventListener("focusin", stopAutoSlide);

            slider.addEventListener("focusout", function (event) {

                if (!slider.contains(event.relatedTarget)) {
                    startAutoSlide();
                }

            });


            // 自動スライド開始
            startAutoSlide();

        }

    }

});
