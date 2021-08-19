SELECT /* windowClosingPosData */
    b.paymentType
     ,MAX(b.gdName) as gdName
     ,b.payMethod
     ,SUM(b.personCnt) as personCnt /* 수량 */
     ,SUM(b.price) as price /* 금액 */
     ,SUM(b.discountCount) as discountCnt /* 할인매수 */
     ,SUM(b.discountAmount) as discount /* 할인금액 */
FROM (
         SELECT
             a.paymentType
              ,a.gdName
              ,a.payMethod
              ,(
             CASE
                 WHEN a.paymentType = 'PAY'
                     THEN a.ticketCount
                 WHEN a.paymentType = 'CANCEL'
                     THEN a.refundCount
                 ELSE 0
                 END
             ) as personCnt /* 수량 */
              ,(
             CASE
                 WHEN a.paymentType = 'PAY'
                     THEN a.ticketAmount
                 WHEN a.paymentType = 'CANCEL'
                     THEN a.refundAmount
                 ELSE 0
                 END
             ) as price /* 금액 */

              ,a.discountCount /* 할인 수량 */
              ,a.discountAmount /* 할인 금액 */

         FROM (
                  SELECT
                      'PAY' as paymentType
                       ,gd.gd_name as gdName
                       ,pm.pm_pay_method as payMethod
                       ,rd.init_rs_ticket_cnt as ticketCount /* 발권매수 */
                       ,(rd.init_rs_ticket_cnt * rd_price) as ticketAmount /* 발권금액 */
                       ,0 as refundCount /* 환불매수 */
                       ,0 as refundAmount /* 환불금액 */
                       ,(
                      CASE
                          WHEN ifnull(rd.dc_unit_price,0) > 0
                              THEN rd.init_rs_ticket_cnt
                          ELSE 0
                          END
                      ) as discountCount /* 할인매수 */
                       ,(rd.init_rs_ticket_cnt * rd.dc_unit_price) as discountAmount
                  FROM t_reserve_detail rd
                           INNER JOIN t_reserve rs
                                      ON rd.rs_seq = rs.rs_seq
                           INNER JOIN t_goods gd
                                      ON rs.gd_seq = gd.gd_seq
                           INNER JOIN t_payment pm
                                      ON rs.rs_seq = pm.rs_seq
                                          AND pm.pm_payment_type = 'PAY'
                  WHERE
                      EXISTS (
                              SELECT
                                  trdp.rs_seq
                              FROM t_reserve_detail_person trdp
                              WHERE trdp.tkt_date = DATE_FORMAT(#{searchDate}, '%Y%m%d')
                                AND trdp.idkey = #{idkey}
         AND trdp.tkt_win_cd = #{winCd}
         AND trdp.rs_seq = rs.rs_seq
         )
                    AND (rs.gd_package_yn = 'Y' OR (rs.rs_package_yn = 'N' AND rs.gd_package_yn = 'N'))
                    AND rs.insert_idkey = #{memberSeq}
                  UNION ALL
                  SELECT
                      'CANCEL' as paymentType
                          ,gd.gd_name as gdName
                          ,pm.pm_pay_method as payMethod
                          ,0 as ticketCount /* 발권매수 */
                          ,0 as ticketAmount /* 발권금액 */
                          ,rd.cancel_ticket_cnt as refundCount /* 환불매수 */
                          ,(rd.cancel_ticket_cnt * rd_price) as refundAmount /* 환불금액 */
                          ,0 as discountCount /* 할인매수 */
                          ,0 as discountAmount
                  FROM t_reserve_detail rd
                      INNER JOIN t_reserve rs
                  ON rd.rs_seq = rs.rs_seq
                      INNER JOIN t_goods gd
                      ON rs.gd_seq = gd.gd_seq
                      INNER JOIN t_payment pm
                      ON rs.rs_seq = pm.rs_seq
                      AND pm.pm_payment_type = 'PAY'
                  WHERE
                      EXISTS (
                      SELECT
                      trdp.rs_seq
                      FROM t_reserve_detail_person trdp
                      WHERE trdp.tkt_date = DATE_FORMAT(#{searchDate}, '%Y%m%d')
                    AND trdp.idkey = #{idkey}
                    AND trdp.cancel_win_cd = #{winCd}
                    AND trdp.cancel_member_seq = #{memberSeq}
                    AND trdp.rs_seq = rs.rs_seq
                      )
                    AND (rs.gd_package_yn = 'Y' OR (rs.rs_package_yn = 'N' AND rs.gd_package_yn = 'N'))
                    AND rs.rs_status = 'D'
              ) a
     ) b
GROUP BY b.paymentType
       ,b.payMethod
ORDER BY b.payMethod, b.paymentType DESC